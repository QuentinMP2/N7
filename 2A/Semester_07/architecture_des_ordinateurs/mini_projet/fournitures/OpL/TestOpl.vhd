--------------------------------------------------------------------------------
-- Company: 
-- Engineer:
--
-- Create Date:   09:45:48 10/23/2024
-- Design Name:   
-- Module Name:   /home/qpu4701/Documents/2A/S7-Architecture_des_ordinateurs/TP/mini_projet/fournitures/OpL/TestOpl.vhd
-- Project Name:  OpL
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: MasterOpl
-- 
-- Dependencies:
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
--
-- Notes: 
-- This testbench has been automatically generated using types std_logic and
-- std_logic_vector for the ports of the unit under test.  Xilinx recommends
-- that these types always be used for the top-level I/O of a design in order
-- to guarantee that the testbench will bind correctly to the post-implementation 
-- simulation model.
--------------------------------------------------------------------------------
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
 
-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--USE ieee.numeric_std.ALL;
 
ENTITY TestOpl IS
END TestOpl;
 
ARCHITECTURE behavior OF TestOpl IS 
 
	-- Component Declaration for the Unit Under Test (UUT)
 
	COMPONENT MasterOpl
	PORT(
		rst : IN  std_logic;
		clk : IN  std_logic;
		en : IN  std_logic;
		v1 : IN  std_logic_vector(7 downto 0);
		v2 : IN  std_logic_vector(7 downto 0);
		miso : IN  std_logic;
		ss : OUT  std_logic;
		sclk : OUT  std_logic;
		mosi : OUT  std_logic;
		val_xor : OUT  std_logic_vector(7 downto 0);
		val_and : OUT  std_logic_vector(7 downto 0);
		val_or : OUT  std_logic_vector(7 downto 0);
		busy : OUT  std_logic
	  );
	END COMPONENT;
    
	COMPONENT SlaveOpl
		PORT(
			sclk : IN std_logic;
			mosi : IN std_logic;
			ss : IN std_logic;          
			miso : OUT std_logic
			);
	END COMPONENT;

   --Inputs
   signal rst : std_logic := '0';
   signal clk : std_logic := '0';
   signal en : std_logic := '0';
   signal v1 : std_logic_vector(7 downto 0) := (others => '0');
   signal v2 : std_logic_vector(7 downto 0) := (others => '0');
   signal miso : std_logic := '0';

 	--Outputs
   signal ss : std_logic;
   signal sclk : std_logic;
   signal mosi : std_logic;
   signal val_xor : std_logic_vector(7 downto 0);
   signal val_and : std_logic_vector(7 downto 0);
   signal val_or : std_logic_vector(7 downto 0);
   signal busy : std_logic;

   -- Clock period definitions
   constant clk_period : time := 10 ns;
   constant sclk_period : time := 10 ns;
	
	-- Type automate
	type t_etat_test is (DEBUT, SESSION1, SESSION2, ATTENTE_DISABLE_EN, ATTENTE_BUSY, FIN);
	signal etat : t_etat_test;
 
BEGIN

   Inst_MasterOpl: MasterOpl PORT MAP (
          rst => rst,
          clk => clk,
          en => en,
          v1 => v1,
          v2 => v2,
          miso => miso,
          ss => ss,
          sclk => sclk,
          mosi => mosi,
          val_xor => val_xor,
          val_and => val_and,
          val_or => val_or,
          busy => busy
        );
		  
	Inst_SlaveOpl: SlaveOpl PORT MAP(
		sclk => sclk,
		mosi => mosi,
		miso => miso,
		ss => ss
	);

   -- Clock process definitions
   clk_process: process
   begin
		clk <= '0';
		wait for clk_period/2;
		clk <= '1';
		wait for clk_period/2;
   end process;
 
	rst <= '0', '1' after 100 ns;

   -- Stimulus process
   stim_proc: process(clk,rst)
		variable cpt_att : natural;
   begin
		if (rst = '0') then
			en <= '0';
			cpt_att := 10;
			v1 <= (others => 'U');
			v2 <= (others => 'U');
			etat <= DEBUT;
		elsif (rising_edge(clk)) then
			case etat is
				when DEBUT =>
					-- attente de 10 cycles de l'horloge
					-- avant que l'esclave soit prêt
					cpt_att := 10;
					v1 <= (others => 'U');
					v2 <= (others => 'U');
					etat <= SESSION1;
				
				when SESSION1 =>
					cpt_att := cpt_att-1;
					if (cpt_att = 0) then
						-- On a attendu assez longtemps
						
						-- Envoi des valeurs v1 et v2
						v1 <= "01010101";
						v2 <= "00010010";
						en <= '1';
						
						cpt_att := 2;
						etat <= ATTENTE_DISABLE_EN;
					end if;
					
				when ATTENTE_DISABLE_EN =>
					-- Attendre un peu avant de repasser en à '0'
					if (cpt_att = 0) then
						en <= '0';
						etat <= ATTENTE_BUSY;
					else
						cpt_att := cpt_att-1;
					end if;
						
				when ATTENTE_BUSY =>
					-- Attendre que les échanges soient finis
					if (busy = '0') then
						etat <= SESSION2;
					end if;
					
				when SESSION2 =>
					-- Donner de nouvelles valeurs
					v1 <= "01101100";
					v2 <= "00110100";
					en <= '1';
					
					-- On a fini le test
					etat <= FIN;

				when FIN => null;
			end case;
		end if;
   end process;

END;
