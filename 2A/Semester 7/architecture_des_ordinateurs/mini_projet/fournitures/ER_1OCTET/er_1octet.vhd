library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity er_1octet is
  port ( rst : in std_logic ;
         clk : in std_logic ;
         en : in std_logic ;
         din : in std_logic_vector (7 downto 0) ;
         miso : in std_logic ;
         sclk : out std_logic ;
         mosi : out std_logic ;
         dout : out std_logic_vector (7 downto 0) ;
         busy : out std_logic);
end er_1octet;

architecture behavioral of er_1octet is

	-- Type automate
	type t_etat is (REPOS, RECEPTION_BIT, ENVOI_BIT);
	signal etat : t_etat;

begin
	process (clk, rst)
		variable cpt_bit : natural; -- nombre de bits à envoyer
		variable rg_din : std_logic_vector (7 downto 0); 
	begin
		
		if (rst = '0') then
			-- On repasse à un état de repos
			-- On réinitialise les variables
			etat <= REPOS;
			busy <= '0';
			sclk <= '1';
			rg_din := (others => '0');
			cpt_bit := 7;
			
		elsif (rising_edge(clk)) then
			case etat is
				when REPOS =>
					-- On attend que le composant soit activé,
					-- auquel cas initialise les variables
					-- pour débuter un envoi de bit.
					if (en = '1') then
						busy <= '1';
						sclk <= '0';
						rg_din := din;
						cpt_bit := 7;
						mosi <= rg_din(cpt_bit);
						etat <= RECEPTION_BIT;
					end if;
					
				when RECEPTION_BIT =>
					if (cpt_bit = 0) then
						-- On est arrivé à la fin de la transmission
						-- des bits, on indique qu'on ne travaille
						-- plus et on tranfère les bits reçus sur
						-- la sortie.
						sclk <= '1';
						busy <= '0';
						rg_din(cpt_bit) := miso;
						dout <= rg_din;
						etat <= REPOS;
					else
						-- On enregistre le bit reçu et
						-- on passe à l'envoie du bit suivant.
						sclk <= '1';
						rg_din(cpt_bit) := miso;
						etat <= ENVOI_BIT;
					end if;
					
				when ENVOI_BIT =>
					-- On envoie un bit, puis
					-- on passe à la réception du suivant.
					sclk <= '0';
					cpt_bit := cpt_bit-1;
					mosi <= rg_din(cpt_bit);
					etat <= RECEPTION_BIT;
			end case;
			
		end if;
		
		
		
	end process;
end behavioral;
