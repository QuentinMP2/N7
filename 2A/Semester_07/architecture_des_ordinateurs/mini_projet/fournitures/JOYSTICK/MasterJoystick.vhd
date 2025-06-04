----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    17:14:58 11/19/2024 
-- Design Name: 
-- Module Name:    MasterJoystick - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity MasterJoystick is
    Port ( rst : in  STD_LOGIC;
           clk : in  STD_LOGIC;
           en : in  STD_LOGIC;
           sclk : out  STD_LOGIC;
           miso : in  STD_LOGIC;
           mosi : out  STD_LOGIC;
           ss : out  STD_LOGIC;
           x : out  STD_LOGIC_VECTOR (9 downto 0);
           y : out  STD_LOGIC_VECTOR (9 downto 0);
           led1 : in  STD_LOGIC;
           led2 : in  STD_LOGIC;
           btnJoy : out  STD_LOGIC;
           btn1 : out  STD_LOGIC;
           btn2 : out  STD_LOGIC);
end MasterJoystick;

architecture Behavioral of MasterJoystick is

	COMPONENT er_1octet
	PORT(
		rst : IN std_logic;
		clk : IN std_logic;
		en : IN std_logic;
		din : IN std_logic_vector(7 downto 0);
		miso : IN std_logic;          
		sclk : OUT std_logic;
		mosi : OUT std_logic;
		dout : OUT std_logic_vector(7 downto 0);
		busy : OUT std_logic
	);
	END COMPONENT;
	
	COMPONENT diviseurClk
	GENERIC(facteur : natural);
	PORT(
		clk : IN std_logic;
		reset : IN std_logic;          
		nclk : OUT std_logic
		);
	END COMPONENT;

	-- Type et variable des états de l'automate
	type t_etat is (REPOS, INIT_ER, ER_UNOCTET);
	signal etat : t_etat;
	
	-- Signaux du composant er_1octet
	signal en_er : std_logic;
	signal busy_er : std_logic;
	signal din_er : std_logic_vector(7 downto 0);
	signal dout_er : std_logic_vector(7 downto 0);
	
	-- Signal diviseur de clock
	signal nclk : std_logic;

	-- Signal pour la valeur temporaire (les bits de poids faible) de x et y
	signal xy_low : std_logic_vector(7 downto 0);

begin

	Inst_er_1octet: er_1octet PORT MAP(
		rst => rst,
		clk => nclk,	-- le composant er_1octet va fonctionner avec une clock 
							-- réduite pour permettre au joystick de suivre la cadence
		en => en_er,
		din => din_er,
		miso => miso,
		sclk => sclk,
		mosi => mosi,
		dout => dout_er,
		busy => busy_er
	);
	
	Inst_diviseurClk: diviseurClk 
	GENERIC MAP(100)
	PORT MAP(
		clk => clk,
		reset => rst,
		nclk => nclk
	);
	
	process(rst,nclk)	-- réveil sur clock réduite
		variable num_octet : natural;
		variable cpt_wait : natural;
	begin
		-- Reset
		if (rst = '0') then
			etat <= REPOS;
			ss <= '1';
			num_octet := 1;
			en_er <= '0';
			din_er <= (others => '0');
			xy_low <= (others => '0');
			cpt_wait := 15;
			
		-- Sur chaque front montant de l'horloge
		elsif (rising_edge(nclk)) then
			case etat is
				when REPOS => 
					if (en = '1') then
						-- C'est le moment de réveiller l'esclave 
						-- et de préparer le terrain pour transmettre
						ss <= '0';	-- on réveille l'esclave
						num_octet := 1;
						en_er <= '0';
						etat <= INIT_ER;
						din_er <= (others => '0');
						xy_low <= (others => '0');
						cpt_wait := 15;
					end if;
				
				when INIT_ER =>
					if (cpt_wait = 0) then
						-- Initialisation de l'octet à transmettre au joystick
						-- afin d'utiliser les deux leds du joystick.
						din_er <= "100000" & led2 & led1;
						
						en_er <= '1';
						etat <= ER_UNOCTET;
					else
						cpt_wait := cpt_wait-1;
					end if;
					
				when ER_UNOCTET =>
					-- On est dans l'état d'envoi et de réception,
					-- on met en_er à 0 pour que ce changement soit
					-- effectif sur le prochain front montant de l'horloge
					-- c'est-à-dire quand on sera dans l'état INIT_ER (ou REPOS)
					en_er <= '0';
					
					if (busy_er = '0' and en_er = '0') then
						-- La transmission d'un octet est terminée,
						-- on récupère le résultat.
						case num_octet is
							when 1 => 
								xy_low <= dout_er;
							when 2 => 
								x <= dout_er(1 downto 0) & xy_low;
							when 3 => 
								xy_low <= dout_er;
							when 4 => 
								y <= dout_er(1 downto 0) & xy_low;
							when others => 
								btnJoy <= dout_er(0);
								btn1 <= dout_er(1);
								btn2 <= dout_er(2);
						end case;
						
						cpt_wait := 10;
						etat <= INIT_ER;
						
						if (num_octet = 5) then
							-- Les 5 octets ont été envoyés.
							-- On a récupéré x_low, x_high, y_low, y_high et les leds.
							-- On retourne au REPOS.
							ss <= '1';
							etat <= REPOS;
						else
							-- On prépare le terrain pour la
							-- transmission suivante.
							etat <= INIT_ER;
							num_octet := num_octet+1;
						end if;
					end if;
			end case;
		end if;
	end process;

end Behavioral;

