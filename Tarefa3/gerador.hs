module Main ( main ) where

import Test.QuickCheck
import System.Environment
import System.Random
import Foreign.Marshal.Unsafe


type Codigo= String
type Nome = String
type Cordenadas = (Float,Float)
type Raio = Float
type NIF = String

-- Ecomenda - codigoE, codigoU, codigoL, peso, codigoProd, nomeProd, quantidade(STRING), preço
produtos = ["AZEITE","OLEO ALIMENTAR","ARROZ","AÇUCAR","FARINHAS","MASSAS SECAS","PAPAS DE FLOCOS","CORN FLAKES/ CEREAIS","PEQ. ALMOÇO","ENCHIDOS","BACALHAU SECO","BOLACHAS","FEIJÃO E GRÃO","TOMATE CONCENTRADO","AZEITONAS","COGUMELOS","ERVILHAS","ESPARGOS","CONSERVAS DE PEIXE","SALSICHAS","BARRAS CEREAIS","CAFÉS E MISTURAS","TORRADOS","CHÁS","CALDOS CUBOS","LEITE","AGUA","SUMOS","SOPA INSTANTANEA","MILHO CONSERVA","SAL","VINAGRE","MEL E DOCES","MARMELADA E GOIABA"]
nomesProprios= ["Santiago","Francisco","João","Afonso","Rodrigo","Martim","Tomás","Duarte","Miguel","Gabriel","Lourenço","Gonçalo","Dinis","Guilherme","Pedro","Salvador","Rafael","Lucas","Tiago","Gustavo","Diogo","Vicente","José","David","Mateus","Simão","António","Diego","Manuel","Henrique","Daniel","Vasco","Bernardo","Enzo","André","Leonardo","Luís","Isaac","Eduardo","Alexandre","Kevin","Matias","Leandro","Filipe","Xavier","Rúben","Samuel","Ricardo","Artur","Valentim","Frederico","Lorenzo","Bryan","Bruno","Nuno","Benjamim","Carlos","Sebastião","Rui","Davi","Hugo","Noah","Joaquim","Mário","Tomé","Fábio","Paulo","Manel","Renato","Jorge","Jaime","Marco","Ângelo","Micael","Liam","Ivan","Matheus","William","Gaspar","Cristiano","Luca","Jonathan","Sérgio","Gil","Ivo","Brian","Arthur","Christian","Vítor","Yuri","Fernando","Dilan","Romeu","Caio","Isac","Emanuel","Sandro","Igor","Moisés","Mauro","César","Josué","Edgar","Elias","Victor","Joel","Alex","Telmo","Denis","Marcelo","Adrien","Álvaro","Dylan","Raúl","Muhammad","Nelson","Dário","Oliver","Thiago","Ismael","Márcio","Ian","Lisandro","Erik","Cláudio","Heitor","Martinho","Leo","Jonas","Wilson","Júlio","Caetano","Benjamin","Adriano","Raphael","Augusto","Martin","Marcos","Eric","Flávio","Mikael","Erick","Jason","James","Danilo","Adam","Levi","Mateo","Kelvin","Nicolas","Sebastian","Cauã","Thomas","Isaque","Misael","Alberto","Israel","Edson","Luan","Lukas","Mark","Ruben","Adriel","Jesus","Domingos","Denzel","Apolo","Ary","Alan","Nicolau","Alfredo","Kévim","Axel","Patrick","Giovani","Gerson","Aron","Luis","Leonel","Wesley","Ari","Roberto","Hélder","Vitor","Fausto","Joshua","Gael","Adonai","Henry","Cristóvão","Nathan","Rogério","Caleb","Fabrício","Valentino","Noé","Luka","Armando","Stefan","Bartolomeu","Anthony","Adrian","Samir","Ravi","Derick","Ryan","Luciano","Estêvão","Theo","Cristian","Anderson","Davide","Abel","Ezequiel","Nataniel","Lucca","Dominic","Théo","Alexander","Isaías","Lopo","Marcus","Maxim","Iúri","Diniz","Amadeu","Oscar","Haniel","Lázaro","Ulisses","Léo","Alexandru","Raul","Jessé","Max","Steven","Ernesto","Leon","Maurício","Mohammad","Felipe","Kendrick","Filip","Juliano","Angélico","Rubén","Édi","Enrique","Lourenzo","Damien","Osvaldo","Aléxis","Zion","Matteo","Omar","Eduard","Zayn","Jayden","Kenny","Rubim","Olavo","Jonatã","Tierri","Bogdan","Kenzo","Aaron","Saulo","Pietro","Kai","Breno","Jair","Helder","Uriel","Luiz","Rayan","Mohammed","Dante","Aiden","Jackson","Fred","Amir","Stephan","Mamadou","Valdemar","Jóni","Evan","Hazael","Fabiano","Ibrahim","Juan","Robert","Michael","Natanael","Aayan","Hélio","Adilson","Éder","Silvestre","Jordan","Tito","Taylor","Logan","Leonard","Júnior","Bento","Kévin","Gui","Aryan","Pablo","Jónatas","Elvis","Joabe","Alonso","Jefferson","Deivid","Darius","Serigne","Eli","Giovanni","Romão","Félix","Daniil","Kaique","Kauã","George","Mathias","Vladislav","Adrián","Rúdi","Kylian","Gilson","Damian","Gastão","Ianis","Azael","Cael","Mickael","Jack","Sam","Angelo","Fabio","Alejandro","Isael","Francesco","Valentin","Aarav","Kirill","Andrei","Mohamed","Dénis","Abner","Roman","Adolfo","Gilberto","Óscar","Daví","Artem","Kelson","Ayan","Antonio","Timur","Lyan","Ruan","Louis","Salomão","Simon","Willian","Dávi","Matthew","Romeo","Kauan","Iago","Tobias","Nilson","Kayden","Mílton","Ahmed","Ibrahima","Julian","Agostinho","Lenny","Abraim","Eliel","Hendrick","Aleks","Vinícius","Ethan","Allan","Abdullah","Jacob","Kevyn","Nikolas","Edmar","Nikita","Vlad","Murilo","Baltasar","Acácio","Elijah","Yaroslav","Dino","Jeremias","Válter","Anselmo","Licínio","Djeison","Baltazar","René","Edmilson","Aleksandr","Thierry","Élson","Eugénio","Oleksandr","Reyansh","Sami","Arham","Edward","Andrew","Yannick","Rodolfo","Ronaldo","Yi","Michel","Lúcio","Maksim","Elísio","Eliab","Eliezer","Gelson","Mihai","Humberto","Elói","Denilson","Viriato","Mason","Mateu","Harry","Sidney","Orlando","Andriy","Davy","Átila","Jairo","Sílvio","Ilídio","Amílcar","Emmanuel","Dani","Nilton","Matvey","Serafim","Mario","Evandro","Arlindo","Kian","Edilson","Mélvin","Ederson","Walter","Keven","Nicolás","Kleyton","Josias","Gonzalo","Adelino","Iker","Marius","Aahan","Demian","Jayson","Arnaldo","Ariel","Ildo","Owen","Anilson","Célio","Eliézer","Délcio","Jean","Musa","Riyansh","Marvin","Aleksander","Djeyson","Dumitru","Maël","Franklin","Theodoro","Áureo","Sandiego","Calebe","Ódin","Abraão","Décio","Fabrizio","Yannis","Leone","Saif","Saliou","Marko","Yuvraj","Kimi","Tian","Gonzaga","Óliver","Benício","Aramis","Fabian","Seco","Vital","Sahas","Matei","Oceano","Amine","Jawad","Youssef","Emir","Jay","Danylo","Jailson","Kiame","Yohan","Jasmim","Brayan","Élio","Jerry","Halley","Eden","Jacinto","Pak","Alessandro","Shayan","Euclides","Gianni","Martín","Aleksey","Dalton","Marlon","Hakeem","Conrado","Jad","Alexandro","Rihan","Hao","Adryan","Lauriano","Vincent","Willyan","Jerónimo","Enzi","Richard","Ayaan","Celso","Yeshua","Cédrick","Lewis","Aylan","Zyon","Fernão","Dimas","Jerson","Gurtej","Francis","Nélson","Dmytro","Albert","Calvin","Mauricio","Viaan","Timóteo","Dorian","Aarnav","Alexey","Gheorghe","Graciano","Anael","Keanu","Aurélio","Matheo","Emerson","Alcides","Kiese","Renan","Briyan","Vagner","Eros","Maksym","Bilal","Algassimou","Jimmy","Ishan","Ruby","Aleksandar","Amândio","Zorawar","Brayn","Ali","Luccas","Moussa","Mariano","Mankirat","Yanis","Sancho","Charlie","Silas","Philipp","Vando","Solano","Reinaldo","Toni","Bernardino","Messias","Sidnei","Kennedy","Abílio","Kiene","Nélio","Matviy","Fedor","Oumar","Braima","Rodney","Aquiles","Kaio","Ícaro","Matías","Valério","Emilio","Lyam","Aleksandre","Eder","Kelton","Zacarias","Dárcio","Maxwell","Christyan","Íven","Mattia","Jaden","Janai","Manassés","Thomás","Taha","Konstantin","Figo","Lincoln","Eleazar","Marcellus","Aires","Estevão","Hayden","Aidan","John","Hernâni","Aarush","Davidson","Derek","Narciso","Adílio","Levy","Prerak","Romário","Alfa","Nicollas","Etienne","Tyler","Mamadú","Manoel","Damião","Jelson","Weslley","Zyan","Bo","Érico","Unai","Aziz","Alvin","Asafe","Prince","Milton","Santhiago","Catalin","Abraham"]
nomesFamilia = ["Abasto","Abelho","Abranches","Abrantes","Abreu","Adão","Adarga","Afonso","Águeda","Aguiar","Aires","Albernaz","Albuquerque","Alcaide","Alcântara","Alcoforado","Aldeia","Aleixo","Alencar","Almada","Almeida","Alpuim","Alvarenga","Álvares","Álvaro","Alvelos","Alves","Alvim","Amado","Amaral","Amarante","Amaro","Amoedo","Amorim","Andrade","Anes","Angelim","Anhaia","Anjos","Anlicoara","Antas","Antunes","Aquino","Aragão","Aranha","Arantes","Araújo","Areosa","Argolo","Arouca","Arruda","Assis","Assunção","Ataíde","Atilano","Aveiro","Avelar","Ávila","Azambuja","Azenha","Azeredo","Azevedo","Bacelar","Baía","Baião","Bairros","Baldaia","Balsemão","Bandeira","Banha","Baptista","Barata","Barateiro","Barbalho","Barbosa","Barcelos","Barra","Barreira","Barreiros","Barrico","Barrela","Barreno","Barreto","Barroca","Barrocas","Barroqueiro","Barros","Barroso","Basílio","Bastos","Batata","Beiriz","Belchior","Belchiorinho","Belém","Belmonte","Belo","Beltrão","Bencatel","Benevides","Bensaúde","Bentes","Bettencourt","Bento","Berenguer","Bernardes","Bessa","Bezerra","Bezerril","Bicalho","Bicudo","Bilhalva","Bingre","Bivar","Boaventura","Boeira","Boga","Bogado","Bonilha","Bonito","Borba","Borges","Borja","Botelho","Botica","Boto","Bouça","Bouças","Brandão","Braga","Bragança","Branco","Brás","Brásio","Brasil","Breia","Brião","Brites","Brito","Brochado","Brum","Bugalho","Bulhões","Bulhosa","Cabeça","Cabeça de Vaca","Cabral","Cabreira","Cachão","Cachoeira","Caçoilo","Cadaval","Cadavez","Caeira","Caeiro","Caetano","Café","Caiado","Caires","Calado","Calçada","Caldas","Caldeira","Calheiros","Camacho","Câmara","Camargo","Camarinho","Cambezes","Camelo","Camilo","Caminha","Campelo","Campos","Canadas","Canário","Cancela","Candal","Candeias","Canedo","Caneira","Canejo","Canela","Cangueiro","Canhão","Caniça","Cantanhede","Canto","Caparica","Capistrano","Capucho","Cardim","Cardoso","Carlos","Carmo","Carmona","Carneiro","Coronel","Carqueijeiro","Carrasco","Carrasqueira","Carreira","Carregueiro","Carreiro","Carrilho","Carromeu","Cartaxo","Carvalhais","Carvalhal","Carvalheira","Carvalheiro","Carvalho","Carvalhosa","Carvalhoso","Casado","Casalinho","Cascais","Casqueira","Castanheira","Castanho","Castanheda","Castelhano","Castelo","Castelo Branco","Castilho","Castilhos","Castro","Catela","Cavadas","Cavaco","Cavalheiro","Cedraz","Cedro","Cerqueira","Cerveira","César","Cesário","Chagas","Chainho","Chamusca","Charneca","Chaves","Chousa","Cidreira","Cipriano","Cirne","Cisneiros","Clementino","Cobra","Coelho","Coimbra","Colaço","Colares","Conceição","Conde","Condorcet","Cordeiro","Correia","Corte-Real","Cortês","Cortesão","Costa", "da Costa","Cotrim","Couceiro","Coutinho","Couto","Covilhã","Covinha","Craveiro","Cruz","Cunha","Curvelo","Custódio","Curado","Damasceno","Damásio","Dâmaso","Dantas","Delgado","Dias","Diegues","Dinis","Dourado","Doutel","Doutis","Domingos","Domingues","Dorneles","Duarte","Durão","Durães","Dutra","Eanes","Eiró","Escobar","Espargosa","Esparteiro","Espinosa","Espírito Santo","Esteves","Estrada","Estrela","Faia","Fagundes","Falcão","Faleiro","Faria","Farias","Farinha","Faro","Fartaria","Faustino","Fazendeiro","Feijó","Feira","Feitosa","Félix","Fernandes","Ferraço","Ferrão","Ferraz","Ferreira","Ferro","Festas","Fiães","Fialho","Fidalgo","Figueira","Figueiredo","Figueiró","Figueiroa","Filgueiras","Filipe","Fitas","Fiúza","Flávio","Flores","Fogaça","Fonseca","Fontes","Fontinha","Fontoura","Foquiço","Fortes","Fortunato","Frade","Fraga","Fragoso","Frajuca","Franca","França","Franco","Franca","Franqueira","Frazão","Freire","Freiria","Freitas","Freixo","Frias","Frois","Frota","Furquim","Furtado","Gabeira","Gadelha","Gago","Galante","Galindo","Galvão","Gama","Gameiro","Garcez","Garcia","Garrau","Garrido","Gaspar","Gentil","Gil","Ginjeira","Girão","Godinho","Godoi","Gois","Gomes","Gomide","Gonçalves","Gorjão","Goulart","Gouveia","Goulão","Graça","Grande","Grangeia","Granja","Granjeiro","Gravato","Grilo","Guedelha","Guedes","Guerra","Guerreiro","Guilheiro","Guimarães","Gusmão","Guterres","Henriques","Hernandes","Hilário","Hipólito","Holanda","Homem","Horta","Igrejas","Ilha","Imperial","Inácio","Inês","Infante","Jamandas","Janes","Jardim","Jesus","Jobim","Jordão","Jorge","Júdice","Junqueira","Keil","Lacerda","Lage","Lages","Lago","Lagoa","Lagos","Lalanda","Lamego","Lameira","Lameirinhas","Lameiras","Lamenha","Lampreia","Lancastre","Landim","Lara","Laranjeira","Lários","Laureano","Leal","Leão","Leiria","Leitão","Leite","Leme","Lemes","Lemos","Lessa","Letras","Liberato","Lima","Linhares","Lindim","Lins","Lira","Lisboa","Lobato","Lobo","Loio","Lopes","Lourenço","Loureiro","Lousã","Lousada","Lousado","Lucas","Lucena","Lustosa","Luz","Macedo","Macena","Machado","Macieira","Maciel","Madeira","Madruga","Madureira","Mafra","Magalhães","Maia","Mainha","Maior","Malafaia","Malheiro","Malho","Malta","Mamouros","Mangueira","Mansilha","Manso","Mantas","Maranhão","Marçal","Marcondes","Marinho","Marins","Mariz","Marmou","Marques","Marreiro","Marroquim","Martinho","Martins","Mascarenhas","Mata","Mateus","Matias","Matos","Matosinhos","Matoso","Medeiros","Medina","Meira","Meireles","Melancia","Melgaço","Mena","Melo","Mendes","Mendonça","Meneses","Mesquita","Mexia","Miguel","Miguéis","Mieiro","Milhães","Milheirão","Milheiriço","Milheiro","Minho","Miranda","Mirandela","Modesto","Moita","Mondragão","Monforte","Monjardim","Monsanto","Mont'Alverne","Monte","Montenegro","Monteiro","Morais","Morão","Moreira","Moreno","Morgado","Mortágua","Mota","Moura","Mourão","Mourato","Mourinho","Moutinho","Muniz","Murteira","Murtinho","Muxagata","Narvais","Nascimento","Natal","Naves","Nazário","Negrão","Negreiros","Negromonte","Neiva","Neres","Neto","Neves","Ninharelhos","Nobre","Nóbrega","Nogueira","Noite","Nolasco","Noleto","Norões","Noronha","Novais","Nunes","Olaio","Oleiro","Olivares","Oliveira","Onofre","Ornelas","Orriça","Osório","Ourique","Ouro","Outeiro","Pacheco","Padilha","Pádua","Paião","Pais","Paiva","Paixão","Palha","Palhares","Palma","Palmeira","Palos","Parafita","Paranhos","Pardo","Paredes","Parente","Parracho","Parreira","Passarinho","Passos","Pastana","Patrício","Paula","Paulos","Paz","Peçanha","Pêcego","Pederneiras","Pedro","Pedroso","Pegado","Peixoto","Penha","Penteado","Pequeno","Peralta","Perdigão","Pereira","Pescada","Peseiro","Pessoa","Pestana","Picanço","Picão","Pimenta","Pimentel","Pinhal","Pinheiro","Pinho","Pinto","Piteira","Pires","Poças","Ponte","Pontes","Porciúncula","Portela","Porto","Portugal","Póvoas","Prada","Prado","Prates","Prestes","Proença","Protásio","Prudente","Pureza","Quadros","Quaresma","Queiroga","Queirós","Quental","Quesado","Quina","Quinaz","Quinta","Quintal","Quintana","Quintanilha","Quintas","Quintais","Quintão","Quinteiro","Quintela","Quinterno","Quinzeiro","Quirino","Rabelo","Ramalho","Raminhos","Ramires","Ramos","Rangel","Raposo","Rebelo","Rebimbas","Rebocho","Rebotim","Rebouças","Redondo","Regalado","Rego","Regodeiro","Regueira","Rei","Reino","Reis","Remígio","Resende","Ribas","Ribeiro","Rico","Rijo","Rios","Robalinho","Robalo","Roçadas","Rocha","Rodovalho","Rodrigues","Rolim","Roriz","Rosa","Rosado","Rosário","Rosmaninho","Rua","Ruas","Ruela","Rufino","Sá","Sabala","Sabrosa","Sacadura","Sacramento","Salazar","Saldanha","Sales","Salgado","Salgueiro","Salvado","Saloio","Salomão","Saltão","Sampaio","Sanches","Sandinha","Santana","Santarém","Santiago","Santos","Saraiva","Sardinha","Sardo","Sarmento","Seabra","Seixas","Semedo","Serpa","Serralheiro","Serro","Sesimbra","Setúbal","Severiano","Severo","Silva","Silveira","Silvestre","Simas","Simões","Simão","Sintra","Sítima","Sequeira","Soares","Sobral","Sobreira","Sodré","Soeiro","Sousa","Souto","Souto Maior","Soveral","Soverosa","Tabanez","Taborda","Tabosa","Talhão","Tavares","Taveira","Taveiros","Távora","Teixeira","Tedim","Teles","Telinhos","Temes","Teodoro","Terra","Teves","Tigre","Tinoco","Toledo","Tomé","Torquato","Torrado","Torreiro","Torres","Toscano","Travassos","Toste","Trigueiro","Trindade","Tristão","Tuna","Uchoa","Ulhoa","Úria","Urias","Valadão","Valadares","Valadim","Valcácer","Valcanaia","Vale","Valente","Valentim","Valério","Valgueiro","Valido","Valim","Valverde","Varanda","Varão","Varejão","Varela","Vargas","Vasconcelos","Vasques","Vaz","Veiga","Velasco","Velasques","Veleda","Veloso","Ventura","Vergueiro","Veríssimo","Viana","Vidal","Vides","Vidigal","Viegas","Vieira","Vigário","Vila-Chã","Vilaça","Vilalobos","Vilanova","Vilante","Vilar","Vilariça","Vilarinho","Vilas Boas","Vilaverde","Vilela","Vilhena","Vinhas","Vital","Viveiros","Xavier","Ximenes","Xisto","Zagalo","Zambujal","Zarco"]
transportadoras = ["Geocargo - Transitários, Lda","Logistaf, Lda","Paulexpress - Transporte De Mercadorias, Lda","Transportes Tufão, Unipessoal, Lda","Iberomail - Correio Internacional, S.a.","Dachser Portugal Air And Sea Logistics, S.a.","Ideal - Expresso - Serviço De Estafetas, Lda","Portomail - Transporte De Documentos E Encomendas, Lda","Sistaexpresso - Transporte De Encomendas Postais,unipessoal., Lda","Loriexpress - Transporte De Encomendas, Lda","M.r.j. - Transportes De Encomendas Urgentes, Lda","Manuel Valente De Almeida & Filhos, Lda","Avintestour - Transporte De Passageiros, Lda","Inforlider - Sociedade De Organização Informática, Lda","Lopes Pereira & Pereira, Lda","Logicentro - Importação E Comercialização De Computadores, Lda","Futurvida - Fabricação De Veículos Especiais, Lda","Transportes Ideal Da Freixeira, Lda","Smartbus - Transportes Personalizados, Lda","Fozpost - Entregas E Recolhas De Encomendas, Lda","Sgate - Sociedade Comercial E De Gestão De Artigos Têxteis, Encomendas E Moda, Lda","Be Rapid - Distribuição De Encomendas, Lda","Mapelgex, Unipessoal, Lda","Plano I9 - Soluções Globais Logísticas, Lda","Jet Express - Empresa De Estafetagem, Lda","Upper Class - Transportes E Serviços, Lda","Ttc - Transportes Teles Do Carmo, Lda","Compressores Betico Portugal, Unipessoal, Lda","Edgar & Prieto, Lda","Central Mensageiro - Serviço De Estafetas Móveis, Lda"] 
lojas=["ACABEHOME","AÇAÍ NATURA","AGÊNCIA ABREU","BERSHKA","BERTRAND LIVREIROS","BLINK","BSTRONGBURGER KING","C&A","CALÇADO GUIMARÃES","CALZEDONIA","CARPET STORE","CASA DAS BOLAS DE BERLIM","CELEIRO","CENTROS ÚNICO","CHATEAU D'AX","CHAVIARTE","CINEPLACE","COFFEE & SOFT","COLCHAONET","CONTINENTE","CORTEFIEL","D'ORO JÓIAS","EQUIVALENZA","ESPAÇO CASA","FLORMAR","FORTE STORE","GATO PRETO – LIVING SPACES","H&M","HAMBURGUERIA DEGEMA","HEBE GYROS","HOME NUT"]


genNome :: Gen Nome
genNome = do a <- elements nomesProprios
             b <- elements nomesFamilia
             return (a++" "++b)

genNumber :: Int -> Gen String
genNumber 0 = return ""
genNumber n = do a <- elements [0..9]
                 b <- genNumber (n-1)
                 return ((show a)++b)

getProducts :: Gen [String]
getProducts = do p <- vectorOf 35 $ elements produtos
                 return (p)


genCoordenada :: Gen Float -- nao sei fazer isto 
genCoordenada = choose (1.0000,4)



-- transportadora (codEmpresa,nomeEmpresa,gps,nif,raio,precoPorKm) 
genNomeTransportadora :: Gen Nome
genNomeTransportadora = do a <- (elements transportadoras) 
                           return a

genCodigo :: Int -> Gen Codigo
genCodigo i = genNumber (length (show i))


genPrecoKm :: Gen Float
genPrecoKm = choose (1.0,1)


genNif :: Gen String
genNif = genNumber 9

genTransportadora :: Int -> [String] -> Gen [String]
genTransportadora 0 _ = return []
genTransportadora n a = do b <- vectorOf n $ genNomeTransportadora
                           x <- vectorOf n $ choose ((-9.32),(-6.32)) -- ficam assim as coordenadas?????
                           y <- vectorOf n $ choose ((37.0),(42.0)) -- ficam assim as coordenadas????? 
                           ni <- vectorOf n $ genNif 
                           r <- vectorOf n $ (genNumber 3)
                           p <- vectorOf n $ genPrecoKm
                           return (genTransportadora' a b x y ni r p)


genTransportadora' :: [Codigo] -> [Nome] -> [Float] -> [Float]-> [NIF] -> [String] -> [Float] -> [String]
genTransportadora' [] _ _ _ _ _ _ = []
genTransportadora' (a:as) (b:bs) (x:xs) (y:ys) (n:ns) (r:rn) (p:pn) = [("Transportadora:"++"t"++a++","++b++","++(show x)++","++(show y)++","++n++","++r++","++(show p))]++(genTransportadora' as bs xs ys ns rn pn)

--gera Utilizador

genUti :: Int -> [String] -> Gen [String]
genUti 0 _ = return []
genUti n b = do d <- vectorOf n $ genNome 
                x <- vectorOf n $ choose ((-9.32),(-6.32))
                y <- vectorOf n $ choose ((37.0),(42.0))
                return (genUti' b d x y)
genUti' :: [Codigo] -> [Nome] -> [Float] -> [Float] -> [String]
genUti' [] _ _ _ = []
genUti' (a:as) (d:ds) (x:xs) (y:ys) = [("Utilizador:"++"u"++a++","++d++","++(show x)++","++(show y))]++(genUti' as ds xs ys)

--gera voluntario

genVol :: Int -> [String] -> Gen [String]
genVol 0 _ = return []
genVol n b = do d <- vectorOf n $ genNome 
                x <- vectorOf n $ choose ((-9.32),(-6.32))
                y <- vectorOf n $ choose ((37.0),(42.0))
                t <- vectorOf n $ choose ((0.0),(200.0))
                return (genVol' b d x y t)
genVol' :: [Codigo] -> [Nome] -> [Float] -> [Float] ->[Float]-> [String]
genVol' [] _ _ _ _ = []
genVol' (a:as) (d:ds) (x:xs) (y:ys) (t:ts) = [("Voluntarios:"++"v"++a++","++d++","++(show x)++","++(show y)++","++(show t))]++(genVol' as ds xs ys ts)



-- loja (codLoja,nomeLoja,gps)

genNomeLoja :: Gen Nome
genNomeLoja = do a <- elements lojas
                 return a

genLoja :: Int -> [String] -> Gen [String]
genLoja 0 _ = return []
genLoja n a = do b <- vectorOf n $ genNomeLoja
                 x <- vectorOf n $ choose ((-9.32),(-6.32)) -- ficam assim as coordenadas?????
                 y <- vectorOf n $ choose ((37.0),(42.0)) -- ficam assim as coordenadas????? 
                 return (genLoja' a b x y)


genLoja' :: [Codigo] -> [Nome] -> [Float] -> [Float] -> [String]
genLoja' [] _ _ _ = []
genLoja' (a:as) (b:bs) (x:xs) (y:ys)= [("Loja:"++"l"++a++","++b++","++(show x)++","++(show y))]++(genLoja' as bs xs ys)



-- x vai ser o numero de encomendas faz se elements [0..10*x-1] se o numero de encomendas for <10 


genAceite :: Int -> [String] -> Gen [String]
genAceite 0 _ = return []
genAceite n codigos = do b <-vectorOf n $ (elements codigos)
                         return (aceites' b)

aceites' :: [String] -> [String]
aceites' [] = []
aceites' (a:as) = [("Aceite:"++"e"++a)]++(aceites' as)


genEncomendas :: Int -> [String] -> [String]->[String]-> Gen [String]
genEncomendas 0 _ _ _ = return []
genEncomendas n a utilizadores lojas  = do u <- vectorOf n $ (elements utilizadores)
                                           l <- vectorOf n $ (elements lojas)
                                           pe <- vectorOf n $ choose((0.01),(150.00))  
                                           num <-  elements[1..35]
                                           p <- genProducts num produtos 
                                           return (genEncomendas' a u l pe p)

genEncomendas' :: [Codigo] -> [Codigo] -> [Codigo] -> [Float] -> [String] -> [String]
genEncomendas' [] _ _ _ _ = []
genEncomendas' _ [] _ _ _ = []
genEncomendas' _ _ [] _ _ = []
genEncomendas' _ _ _ [] _ = []
genEncomendas' _ _ _ _ [] = []
genEncomendas' (a:as) (u:us) (l:ls) (pe:pes) (p:ps) = [("Encomendas:"++"e"++a ++","++"u"++u++","++"l"++l++","++(show pe)++","++p)] ++(genEncomendas' as us ls pes ps)

concatE :: [String] -> String
concatE [p] = p
concatE (p:ps) = p ++ "," ++ (concatE ps)         

genProducts :: Int -> [String] -> Gen [String]
genProducts 0 _ = return []
genProducts n a = do cod <- vectorOf n $ genCodigo 2
                     quantidade <- vectorOf n $ choose((0.000001),(20.000000))
                     valor <- vectorOf n $ choose((0.100000),(60.000000))
                     s <-(genProducts (n-1) a)
                     return ((++)([concatE(genP' cod a quantidade valor)]) s  )

genP' :: [Codigo] -> [String] -> [Float] -> [Float] -> [String]
genP' [] _ _ _ = []
genP' (a:as) (b:bs) (c:cs) (d:ds) = ["p"++a++","++b++","++(show c)++","++(show d)]++(genP' as bs cs ds)

-- ./gerador transportadoras

main = do args <- getArgs
          let arg0 = head args
          let n0 = (read arg0) :: Int
          stringUtilizadores <- generate $ vectorOf n0 $ genCodigo n0
          utilizadores <- generate $ genUti n0 stringUtilizadores
          mapM_ putStrLn utilizadores

          let arg1 = args !! 1  
          let n1 = (read arg1) :: Int
          stringVoluntarios <- generate $ vectorOf n1 $ genCodigo n1
          voluntarios <- generate $ genVol n1 stringVoluntarios
          mapM_ putStrLn voluntarios

          let arg2 = args !! 2
          let n2 = (read arg2) :: Int
          stringTransportadora <- generate $ vectorOf n2 $ genCodigo n2
          transportadoras <- generate $ genTransportadora n2 stringTransportadora
          mapM_ putStrLn transportadoras
          
          let arg3 = args !! 3
          let n3 = (read arg3) :: Int
          stringLoja <- generate $ vectorOf n3 $ genCodigo n3
          lojas <- generate $ genLoja n3 stringLoja
          mapM_ putStrLn lojas 
          
          let arg4 = args !! 4
          let n4 = (read arg4) :: Int
          stringEncomenda <- generate $ vectorOf n4 $ genCodigo n4

          encomendas <- generate $ genEncomendas n4 stringEncomenda stringUtilizadores stringLoja
          mapM_ putStrLn encomendas


          aceites <-generate $ genAceite n4 $ stringEncomenda
          mapM_ putStrLn aceites 



