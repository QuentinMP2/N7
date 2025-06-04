library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity MasterOpl is
	port (rst : in std_logic;
			clk : in std_logic;
			en : in std_logic;
			v1 : in std_logic_vector(7 downto 0);
			v2 : in std_logic_vector(7 downto 0);
			miso : in std_logic;
			ss   : out std_logic;
			sclk : out std_logic;
			mosi : out std_logic;
			val_xor : out std_logic_vector(7 downto 0);
			val_and : out std_logic_vector(7 downto 0);
			val_or : out std_logic_vector(7 downto 0);
			busy : out std_logic
	);
end MasterOpl;

architecture behavior of MasterOpl is

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

	-- Type et variable des états de l'automate
	type t_etat is (REPOS, WAIT_INIT_ER, ER_UNOCTET);
	signal etat : t_etat;
	
	-- Signaux du composant er_1octet
	signal en_er : std_logic;
	signal busy_er : std_logic;
	signal din_er : std_logic_vector(7 downto 0);
	signal dout_er : std_logic_vector(7 downto 0);
	
begin

	Inst_er_1octet: er_1octet PORT MAP(
		rst => rst,
		clk => clk,
		en => en_er,
		din => din_er,
		miso => miso,
		sclk => sclk,
		mosi => mosi,
		dout => dout_er,
		busy => busy_er
	);

	process(rst,clk)
		variable cpt_wait : natural;
		variable num_octet : natural;
	
	begin
		-- Reset
		if (rst = '0') then
			etat <= REPOS;
			ss <= '1';
			busy <= '0';
			cpt_wait := 9;
			num_octet := 1;
			en_er <= '0';
		
		-- Sur chaque front montant de l'horloge
		elsif (rising_edge(clk)) then
			case etat is
				when REPOS => 
					if (en = '1') then
						-- C'est le moment de réveiller l'esclave 
						-- et de préparer le terrain pour transmettre
						ss <= '0';	-- on réveille l'esclave
						busy <= '1';
						cpt_wait := 9;
						num_octet := 1;
						en_er <= '0';
						etat <= WAIT_INIT_ER;							
					end if;
				
				when WAIT_INIT_ER =>
					-- L'escalve est réveillé, on attend 10 tics d'horloge
					-- pour qu'il puisse être opérationnel.
					if (cpt_wait = 0) then
						etat <= ER_UNOCTET;
						
						-- On donne les valeurs à din_er
						-- AVANT d'activer le composant er_1octet.
						case num_octet is
							when 1 => din_er <= v1;
							when 2 => din_er <= v2;
							when others => din_er <= "00000000";
						end case;
						en_er <= '1';
					else
						-- L'esclave n'est pas encore opérationnel.
						cpt_wait := cpt_wait-1;
					end if;
					
				when ER_UNOCTET =>
					-- On est dans l'état d'envoi et de réception,
					-- on met en_er à 0 pour que ce changement soit
					-- effectif sur le prochain front montant de l'horloge
					-- c'est-à-dire quand on sera dans l'état WAIT_INIT_ER (ou REPOS)
					en_er <= '0';
					
					if (busy_er = '0' and en_er = '0') then
						-- La transmission d'un octet est terminée,
						-- on récupère le résultat.
						case num_octet is
							when 1 => val_xor <= dout_er;
							when 2 => val_and <= dout_er;
							when others => val_or <= dout_er;
						end case;
						
						if (num_octet = 3) then
							-- Les 3 octets ont été envoyés.
							-- On a récupéré le xor, le and et le or.
							-- On retourne au REPOS.
							ss <= '1';
							busy <= '0';
							etat <= REPOS;
						else
							-- On prépare le terrain pour la
							-- transmission suivante.
							cpt_wait := 2;
							etat <= WAIT_INIT_ER;
							num_octet := num_octet+1;
						end if;
					end if;
			end case;
		end if;
	end process;

end behavior;
