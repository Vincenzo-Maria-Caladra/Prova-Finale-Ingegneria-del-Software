<h1 style="text-align: center"> Documentazione "Bacheca UniCollege" </h1>

<h3 style="text-align: center">Prova finale Ingegneria del Software</h3>

<h4 style="text-align: center">Autore: Calandra Vincenzo Maria</h4>

<h5 style="text-align: center">Matricola: 914651</h5>

<h2 id="Indice">Indice</h2>

- <a href="#Contesto">Contesto</a>
- <a href="#Soluzione">Soluzione</a>
- <a href="#Risultati">Risultati</a>
  - <a href="#LoginPage">Login Page</a>
  - <a href="#SignUpPage">Sign Up Page</a>
  - <a href="#HomeBacheca">Home Bacheca</a>
  - <a href="#DettaglioAttivita">Dettaglio Attivita</a>
  - <a href="#CreaAttivita">Crea Attivita</a>
  - <a href="#Creditometro">Creditometro</a>
  - <a href="#HomeDirettore">Home Direttore</a>
  - <a href="#GestioneAttivita">Gestione Attivita</a>
  - <a href="#HomeTutor">Home Tutor</a>
  - <a href="#HomeSegreteria">Home Segreteria</a>
- <a href="#Tecnologie">Tecnologie</a>
- <a href="#BaseDiDati">Base di dati</a>
- <a href="#UseCase">Use Case</a>
- <a href="#DettaglioImplementazioniTecnologiche">Dettaglio Implementazioni Tecnologiche</a>
  - <a href="#GestioneAutenticazioneAutorizzazione">Gestione Autenticazione Autorizzazione</a>
    - <a href="#Autorizzazione">Autorizzazione</a>
    - <a href="#Autenticazione">Autenticazione</a>
    - <a href="#Registrazione">Registrazione</a>
    - <a href="#MailService">MailService</a>
  - <a href="#Web">Web</a>
    - <a href="#SpringMVC">Spring MVC</a>
    - <a href="#Templating">Templating</a>
    - <a href="#SalvataggioImmagini">Salvataggio Immagini</a>
  - <a href="#Testing">Testing</a>
  - <a href="#DeployEInstallazione">Installazione e Deploy</a>
    - <a href="#Maver">Maven</a>
    - <a href="#Docker">Docker</a>
  - <a href="#LoggingEMonitoring">Logging E Monitoring</a>
    - <a href="#Logging">Logging</a>
    - <a href="#Monitoring">Monitoring</a>
  - <a href="#ClassDiagram">Class Diagram</a>
- <a href="#Repository">Repository</a>


<h2 id="Contesto">Contesto</h2>

<div style="text-align: justify">
La residenza universitaria “UniCollege” permette la permanenza nella struttura subordinata al
conseguimento di determinati obblighi formativi.
Tali obblighi formativi consistono nell’esecuzione di determinate mansioni all’interno della
residenza, nello svolgimento di attività culturali e sociali e nell’approfondimento delle proprie
conoscenze attraverso la lettura di libri e nella preparazione di tertulie a tema. 
</div>

<div style="text-align: justify">
Le attività vengono valutate attraverso un sistema decimale che tiene conto del numero di ore
richiesto per lo svolgimento di una determinata attività e dell’impegno richiesto. 
Ogni studente che svolge un attività matura dunque dei “crediti” che dovranno essere segnati sul
“creditometro”, un foglio excel pre-formattato e condiviso, e infine validati dal tutor dello studente
residente.
</div>

<div style="text-align: justify">
Ogni studente deve conseguire un tot di crediti entro il primo semestre e un altro tot entro il secondo
semestre per poter proseguire il percorso interno al college rispettivamente nel secondo semestre e
l’anno seguente.
</div>

<div style="text-align: justify">    
Relativamente alle attività sociali e culturali gli studenti devono creare una locandina che contiene
data, ora, luogo, max n. di partecipanti e descrizione. Tale locandina dovrà essere approvata dal
direttore della residenza, stampata presso la segreteria e successivamente affissa in bacheca e infine
dovrà essere comunicata l’affissione a tutti gli studenti tramite un messaggio sul gruppo degli
studenti residenti. Ad attività conclusa lo studente organizzatore dell’attività dovrà comunicare in
segreteria gli studenti che hanno partecipato all’attività per la validazione dei crediti conseguiti.
</div>

<div style="text-align: justify">    
Per quanto riguarda la lettura di libri, lo studente dovrà scegliere un libro che intende leggere,
richiedere al tutor di residenza se è possibile ricevere crediti dalla lettura del suddetto libro, leggere
il libro, effettuare la discussione di valutazione con il tutor e infine caricare il titolo del libro letto e
il numero di crediti ricevuto sul creditometro.
</div>

<div style="text-align: justify">
Infine le tertulie a tema richiedono la preparazione di una discussione su una determianta tematica,
la scelta di un giorno della settimana in cui effettuare tale discussione, l’approvazione da parte della
segreteria della data scelta e infine ad attività eseguita la validazione e il carimento dei crediti
conseguiti sul creditometro.
</div>

<div style="text-align: justify">
A fine primo semestre il tutor dovrà assicurasi che gli studenti a suo carico abbiano conseguito i 
crediti necessari per proseguire la permanenza in residenza.
</div>

<h2 id="Soluzione">Soluzione</h2>

<div style="text-align: justify">
    Una possibile alternativa a tale processo può essere un sistema informatico interattivo che dia la
possibilità ai tutor di validare i crediti degli studenti senza dover ogni volta scaricare un file excel e
aggiornarlo manualmente; che dia la possibilità agli studenti resisdenti di pubblicare le attività su
una bacheca digitale e informare i conviventi automaticamente per email dell’avvenuta affissione e
al tempo stesso permetta al direttore della residenza di poter accettare/rifiutare le attività; che mostri
una lista di libri già precedentementi approvati dai tutor della residenza per il conseguitmento dei
crediti e che permetta di proporne dei nuovi; che permetta al segretario la supervisione del
calendario delle tertulie a tema e che visualizzi un report su tutti gli studenti e varie statistiche utili e
infine che permetta una gestione facilitata di inserimento di tutte le altre attività che maturano
crediti su una versione innovata del “creditometro”.
</div>

<h2 id="Risultati">Risultati</h2>

La soluzione proposta così si presenta:

<h3 id="LoginPage">Login Page</h3>

<img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\LoginPage.PNG" alt="LoginPage" style="zoom:50%;" />

Così si presenta la pagina di accesso alla web app. Essa permettere di accedere alla piattaforma tramite email e password o di passare ad una apposita pagina di registrazione. Di default vi è un account con utenza di tipo direttore attraverso cui accedere al portale. Tale utenza è identificata con email residenza.elis.bacheca@gmail.com e password *elis1928*.

<h3 id="SignUpPage">Sign Up Page</h3>

<img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\RegistrationForm.PNG" alt="RegistrationForm" style="zoom:50%;" />

<div style="text-align: justify">
    Tale form permette la registrazione sul portale. Esso richiede nome, cognome email e passoword del nuovo utente da registrare. Ogni campo è obbligatorio e inoltre la email inserita non deve già essere stata utilizzata. Per quanto riguarda la passoword essa deve contenere almeno una lettera, in caps e lower case, almeno un numero, un simbolo speciale e deve avere un minimo di 8 caratteri. Una volta generata una password che specifica la richiesta è necessario reintrodurla nell'apposito input text sottostante, per procedere è necessario che le 2 password coincidano. Una volta cliccato sul pulsante  "Registrati" si verrà reindirizzati su una pagina di conferma e verrà mandata una mail di convalida account alla email specificata.
</div>


<img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\ConfirmRegistration.PNG" alt="ConfirmRegistration" style="zoom:50%;" />

La mail generata avrà l'aspetto seguente:

![ConfirmationEmail](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\ConfirmationEmail.png)

Nella mail possiamo trovare un messaggio di benvenuto personalizzato e un link attraverso cui confermare l'iscrizione alla web app. Il link avrà una validità di 15 minuti, dopodichè l'account creato verrà bloccato.

Una volta cliccato sul link, sel non è ancora scaduto si approderà sulla seguente successful page:

<img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\ConfirmEmailMessage.PNG" alt="ConfirmEmailMessage" style="zoom:50%;" />

<h3 id="HomeBacheca">Home Bacheca</h3>

Una volta loggati con successo la seguente landing page ci accoglierà sul portale mostrandoci tutte le attività che sono state programmate per i giorni successivi.

In alto troviamo una navigation bar attraverso cui moversi all'interno dell web app e un button attraverso cui chiudere la sessione sulla web app.

![Bacheca](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\Bacheca.PNG)

Ogni card rappresenta un attività distinta dalle altre e son caratterizzate dagli attributi principali che descrivono un'attività.  Su ogni card troviamo un apposito pulsante attraverso cui è possibile accedere al dettaglio dell'attività.

<h3 id="DettaglioAttivita">Dettaglio Attività</h3>

La pagina dettaglio attività così si presenta:

![DettaglioAttivita](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\DettaglioAttivita.png)

Come è possibile facilmente constatare, essa contiene l'elenco dei partecipanti e degli organizzatori dell'attività, il numero di posti disponibili, l'immagine di locandina, la descrizione dell'attività e una coppia di bottoni esclusivi tra loro che permettono di iscriversi o cancellarsi dall'attività. 

![](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\DettaglioAttivita2.png)

<h3 id="CreaAttivita">Crea Attività</h3>

Attraverso la barra di navigazione in alto è possibile raggiungere la pagina dedicata alla creazione delle attività.  E' possibile creare tre tipologie differenti di attività, suddivise per informazioni necessarie per la loro creazione.



<img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\Ce.PNG" alt="Ce" style="zoom:50%;" />



Cliccando sulle rispettive categorie si apriranno i seguenti tabs, il primo è attività generica:

![NuovaAttivita](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\NuovaAttivita.PNG)

Tertulia a tema:

![NuovaAttivita2](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\NuovaAttivita2.PNG)

Libro:

![NuovaAttivita3](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\NuovaAttivita3.PNG)

I campi sono tutti obbligatori. Una volta cliccato sul pulsante invia l'utente sarà inviato alla pagina principale e sarà mostrato un messaggio di operazione completata con successo.

Per quanto riguarda il tab "Libro" esso permette di scegliere se proporre un nuovo libro oppure se sceglierne uno già approvato.

![](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\LibroConsigliato.PNG)

<h3 id="Creditometro">Creditometro</h3>

Ogni studente avrà accesso al proprio report personale di attività svolte nella seguente pagina:

![CreditometroAttivitaDaApprovare](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\CreditometroAttivitaDaApprovare.PNG)

Come possiamo vedere troviamo 2 sezioni sostanziali. Una sezione contenente le barre di stato indicanti il numero dei crediti già approvati, in blu, e non approvati, in giallo, e una seconda sezione contenente il dettaglio delle attività svolte.

<h3 id="HomeDirettore">Home Direttore</h3>

Il direttore ha accesso ad una pagina di gestione degli utenti della web app:

![](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\HomeDirettore.PNG)

Da tale pagina è possibile cambiare il ruolo all'interno della web app dei singoli account tra i seguenti tipi: STUDENTE, TUTOR, SEGRETERIA E DIRETTORE. Inoltre è anche possibile settare il tutor dello user selezionato oppure eliminare un account.

<h3 id="GestioneAttivita">Gestione Attività</h3>

Il direttore ha anche accesso ad una pagina di gestione attività:

![](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\AttivitaDaApprovare.PNG)

All'interno vengono visualizzate tutte le attività da approvare tranne i libri, che vengono gestiti direttamente dai singoli tutor. Il direttore può scegliere se approvare o rifiutare un attività oppure vederne il dettaglio cliccando sul titolo. Quando un attività viene approvata vengono notificati tutti gli utenti dell'app.

<h3 id="HomeTutor">Home Tutor</h3>

Per quanto riguarda i tutor, essi avranno a disposizione una pagina dedicata attraverso cui approvare i crediti dei propri mentee.

![ApprovazioneCrediti](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\ApprovazioneCrediti.PNG)

Sulla sinistra troviamo la lista dei mentee con relativa percentuale di crediti approvati, in blue, e crediti non approvati, in giallo. Cliccando sui rispettivi nomi è possibile aprire una seconda table attraverso cui approvare le attività svolte dal mentee selezionato.

<h3 id="HomeSegreteria">Home Segreteria</h3>

La segreteria ha accesso ad un area riservata alla gestione degli studenti, in cui può visualizzarne le informazioni essenziali, e  una pagina dedicata alla gestione delle tertulie a tema, vediamola nel dettaglio.

![TertulieATema](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\TertulieATema.PNG)

Cliccando su aggiorna verrà mostrato un secondo menù di modifica attività oppure è possibile accettare o rifiutare direttamente l'attività. Quando una tertulia viene accettata vengono notificati tutti gli utenti dell'app.


<h2 id=" BaseDiDati"> Base di dati</h2>

Di seguito il diagramma della base di dati.

<img src="C:\Users\CalandraVM\Desktop\Prova Finale\ER_DB_Diagram.drawio.svg" alt="RE DB schematic" style="zoom: 150%;" />

<h2 id="UseCase">Use Case</h2>

![Use Case Diagram](C:\Users\CalandraVM\Desktop\Prova Finale\Use Case Diagram.drawio.svg)

<h2 id="Tecnologie">Tecnologie</h2>

<div style="text-align: justify">
Per il conseguimento di tali obiettivi è stato scelto come linguaggio di programmazione JAVA 11, a oggi esiste un numero notevole di applicazioni e siti web che fanno uso di questo linguaggio. <br><br>Infatti JAVA  è un linguaggio ad alto livello orientato agli oggetti con una forte tipizzazione statica che si appoggia sulla omonima piattaforma, JVM, per questo motivo JAVA, che è linguaggio sia compilato che interpretato, riesce a girare su un numero considerevole di dispositivi differenti con prestazioni mediocri in termini di tempo di esecuzione e prestazioni.<br><br>
Grazie a queste sue caratteristiche JAVA, nelle sue varie versioni, ha creato intorno a se nel corso degli anni un forte interesse da parte delle community di sviluppatori che hanno creato innumerevoli librerie e framework per questo linguaggio.<br><br>
A tal proposito si è scelto di adottare Spring Boot come framework per lo sviluppo web della applicativo. Spring Boot è un evoluzione del già noto Spring, il framework di JAVA più utilizzato. Spring permette di programmare in maniera facile, veloce e sicura grazie ad una serie di librerie native che implementano le best practice per il soddisfacimento di vari use-case in termini di sicurezza e performance in grado di collaborare tra loro permettendo così allo sviluppatore di poter concentrare i propri sforzi non sulla tecnologia ma sulla logica di business che dovrà animare l'applicativo sviluppato. <br><br>Spring Boot introduce un livello di astrazione ancora superiore, esso infatti implementa una gestione facilitata delle configurazioni delle singole librerie del framework grazie ad un file di configurazione globale e una gestione automatica delle compatibilità delle versioni tra le varie librerie del framework. L'uso commerciale di Spring è regolato dalla licenza Apache 2.0.
<div style="text-align: justify">
    Per quanto riguarda la gestione del processo di build del progetto è stato scelto Maven. Maven è un framework dichiarativo di gestione del progetto che segue specifiche convenzioni per quanto riguarda la struttura dello stesso. Maven fa il build di un progetto usando il suo Project Object Model (POM) file e un insieme di plugin. Una volta che si familiarizza con un progetto Maven si è in grado di conoscere qualsiasi progetto Maven e ciò fa risparmiare molto tempo allo sviluppatore. In questo progetto sono state utilizzate principalmente le funzioni di gestione delle dependencies usate nel progetto, di build, di unit testing report e coverage. 
</div>


<div style="text-align: justify">
    Per quanto riguarda la persistenza dei dati la scelta è ricaduta su una database SQL di largo utilizzo, MySQL. Tale tecnologia permette alte prestazioni e scalabilità su Online Transaction Processing (OLTP) application ed è transaction safe, in quanto supporta pienamente operazioni ACID. Inoltre è molto semplice da utilizzare ed è distribuito in una versione Community con licenza GPL, General Public License. <br> <br>I software che vengono distribuiti con tale licenza possono essere utilizzati per tutti gli scopi, inclusi scopi commerciali e persino come strumento per la creazione di software proprietario.
</div>
<div style="text-align: justify">
	Infine si è scelto di effettuare il deploy dell'applicativo utilizzando la containerizzazione piuttosto che la virtualizzazione. La conteinerizzazione permette maggiore scalabilità e gestione delle risorse rispetto alla virtualizzazione, in questo modo ogni applicativo viene eseguito in processi che consumano il minimo delle risorse e che sono isolati tra loro. Inoltre la conteinerizzazione permette d'impacchettare il software con tutte le sue dependencies e distribuirlo da un computing env ad un altro senza alcun problema di compatibilità. L'implementazione scelta, nonchè la più famosa e quella su cui si è poi basato lo standard di mercato,"containerd" della CNCF, è Docker. Docker Engine ha un piano di utilizzo gratuito a uso personale o commerciale regolato dalla licenza Apache 2.0. </div>


<h2 id="DettaglioImplementazioniTecnologiche"> Dettaglio implementazioni tecnologiche</h2>

<h3 id="GestioneAutenticazioneAutorizzazione">Gestione Autenticazione e Autorizzazione</h3>

Le esigenze di progetto hanno rivelato la necessità dell'implementazione di un layer di security nella web application, in particolar modo è venuto fuori dalle analisi che:

- Occorre permettere l'accesso al portale solo ad utenti registrati, per tenere traccia dei fruitori del portale e per permettere future implementazioni correlate.
- Evitare che gli studenti abbiano accesso ad aree del portale riservate alla segreteria, alla direzione e ai tutor.

Per tale motivo si è deciso di utilizzare le librerie di Spring Security per l'implementazione di quanto citato sopra. 

Quando si lavora con Spring Boot, lo *spring-boot-starter-security* ingloba tutte le dependecies necessarie per l'implementazione di un layer di security all'interno dell'applicativo, come ad esempio *spring-security-core*, *spring-security-web*, e spring-security-config. Nel file *pom.xml* troviamo:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

La classe di configurazione di Spring Security estende la classe astratta *WebSecurityConfigurerAdapter.*

Aggiungendo l'annotation *@EnableWebSecurity* attiviamo l'integrazione tra Spring MVC e Spring Security:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public SecurityConfig(UserService userService, BCryptPasswordEncoder 					bCryptPasswordEncoder) {
		super();
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
```

A partire da Spring 5, occorre definire un password encoder. Nel nostro caso la scelta è ricaduta su **BCryptPasswordEncoder** definito nella classe *PasswordEncoder* al'interno del package security:

```java
@Configuration
public class PasswordEncoder {
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
```

Tale password encoder è poi inglobato nella configurazione globale di Spring Security grazie all'annotation *@Bean*.

BCrypt negli ultimi anni è diventato uno standard nell'hashing delle password, è basato sull'agoritmo blowfish ed aggiunge un salt ed un fattore di costo all'hashing, in questo modo è resistente ad attacchi di tipo *rainbow table*. Inoltre è una funzione adattiva, cioè la sua complessità aumenta con il numero di iterazione, per tale motivo è anche resistente ad attacchi di tipo *brute force*. Da una serie di crypto-analisi è poi emerso che BCrypt è molto più complesso da "crakkare" rispetto a SHA-512, in quanto quest'ultimo è messo a dura prova dal calcolo parallelizzato e dall'utilizzo di GPU sempre più potenti a differenza di BCrypt che non subisce tali fattori.

<h4 id="Autorizzazione">Autorizzazione</h4>

A questo punto possiamo procedere con il settaggio delle autorizzazioni sulle richieste ai vari endpoint. La configurazione base prevede l'accesso anonimo egli endpoint /, /login, /registration e /error. In questo modo gli utenti possono registrarsi e autenticarsi.

```java
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests()
				.antMatchers("/registration/**", "/login/**", "/error/**", "/", 						"/confirmationPage/**").permitAll()
				.antMatchers("/homeDirettore/**").hasAuthority("DIRETTORE")
				.antMatchers("/gestioneAttivita/**").hasAuthority("DIRETTORE")
				.antMatchers("/homeTutor/**").hasAuthority("TUTOR")
				.antMatchers("/homeSegreteria/**").hasAuthority("SEGRETERIA")
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/login").defaultSuccessUrl("/homePage", 					true)
				.failureUrl("/login?error=true")
				.and().logout()
				.logoutSuccessUrl("/")
				.logoutUrl("/perform_logout")
				.invalidateHttpSession(true);
		
	}
```

Inoltre si è ristretto l'accesso agli endpoint /homeDirettore e /gestioneAttivita ai soli utenti etichettati come DIRETTORE. In maniera analoga si è proceduto con gli endpoint /homeTutor e /homeSegreteria rispettivamente per gli utenti etichettati come TUTOR e SEGRETERIA.

```java
.antMatchers("/homeDirettore/**").hasAuthority("DIRETTORE")
.antMatchers("/gestioneAttivita/**").hasAuthority("DIRETTORE")
.antMatchers("/homeTutor/**").hasAuthority("TUTOR")
.antMatchers("/homeSegreteria/**").hasAuthority("SEGRETERIA")
```

<h4 id="Autenticazione">Autenticazione</h4>

Tutti gli altri accessi al portale sono vincolati alla sola autenticazione tramite /login.

```java
.anyRequest().authenticated()
.and().formLogin().loginPage("/login").defaultSuccessUrl("/homeBacheca",true)
```

Da notare che l'ordine degli elementi contrassegnati da *antMatchers()* è significativo, le regole più specifiche sono poste prima di quelle generali. 

Di seguito sono riportate le configurazioni di logout:

```java
.and().logout()
.logoutSuccessUrl("/")
.logoutUrl("/perform_logout")
.invalidateHttpSession(true);
```

Da notare inoltre che sono stati cambiati tutti i valori di default degli endpoint, questa buona pressi evita che il framework sia facilmente identificabile dall'esterno, evitando così attacchi mirati.

Il login form di Spring deve contenere i seguenti attributi:

- *login* – lo URL dove effettuare la chiamata POST.
- *username* – lo username.
- *password* – la password.

```html
 <form action="login" method="post">
      <div class="form-floating">
        <label for="username"></label>
        <input type="text" class="form-control" name="username" id="floatingInput" aria-          describedby="emailHelpId"
          placeholder="your.email@example.com">
      </div>

      <div class="form-floating">
        <label for="password"></label>
        <input type="password" class="form-control" name="password" id="floatingPassword"          placeholder="password"> <br>
      </div>

      <div class="form-floating d-flex flex-column">
        <input class="w-100 btn btn-lg btn-primary mr-3" type="submit" value="Log in" />
        <a th:href="@{/registration}" href="#" class="mt-2 font-weight-light font-                size">First time?Click to Sign Up!</a>
      </div>
      <p class="mt-5 mb-3 text-muted">Powered by Vector Code</p>
</form>
```

Tale fom è contenuto all'interno della file login.html nella cartella *src/main/resources/templates*. Tale path è il percorso che viene utilizzato da Spring per servire i contenuti dinamici della web app.

<img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\LoginPage.PNG" style="zoom:50%;" />



A questo punto occorre chiedersi: "Dove avviene tutta la magia dell'autenticazione?". La risponda è semplice, occorre dichiarare un provider che si occupi di tutto  ciò:

```java
@Bean
public DaoAuthenticationProvider daoAuthenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setPasswordEncoder(bCryptPasswordEncoder);
		provider.setUserDetailsService(userService);

		return provider;
}
```

In questo caso viene detto al provider che l'autenticazione va effettuata usando il bcryptPasswordEncoder dichiarato precedentemente e lo *userService*, una classe che si occupa esclusivamente di interagire con la entity *AppUser* e che implementa l'interfaccia *UserDetailService* che richiede di implementare il seguente metodo:

```java
@Service
public class UserService implements UserDetailsService {
    ...
        
    // Login a user by username (email)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException 	{
		
		if (email == null || email.isEmpty() || email.isBlank()) {
			throw new UsernameNotFoundException("Email could not be null!");
		}
		
		Optional<AppUser> useOptional = userRepository.findByEmail(email);
		
		if (useOptional.isPresent()) {
			return useOptional.get();
		} else {
			throw new UsernameNotFoundException("Unable to find user with e: " + email);
		}
	}
    
    ...
```

Grazie a tale  override è possibile effettuare il login alla web app usando come username la mail dell'utente, che è univoca.

<h4 id="Registrazione">Registrazione</h4>

Per quanto riguardo la registrazione si è adottato un meccanismo di verifica a 2 passaggi, di seguito sono elecanti i punti principali:

- Registrazione su apposita pagina raggiungibile tramite endpoint "/registration" o tramite opportuna page di login.

  <img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\RegistrationForm.PNG" alt="RegistrationForm" style="zoom:50%;" />

- Conferma dell'avvenuta registrazione correttamente o incorrettamente.

  <img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\ErrorInRegistrationForm.PNG" alt="ErrorInRegistrationForm" style="zoom:50%;" />

  Oppure:

  <img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\ConfirmRegistration.PNG" alt="ConfirmRegistration" style="zoom:50%;" />

- Ricevimento della mail contenente il token nella URL di conferma.

  ![ConfirmationEmail](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\ConfirmationEmail.png)





- Messaggio di success:

  <img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\ConfirmEmailMessage.PNG" alt="ConfirmEmailMessage" style="zoom:50%;" />

Il processo di registrazione segue dunque un percorso un po' più articolato rispetto all'autenticazione, per tale motivo si è provvisto a creare una rappresentazione grafica opportuna tramite sequence diagram.

![RegistrationSequenceiagram](C:\Users\CalandraVM\Desktop\Prova Finale\RegistrationSequenceiagram.svg)

In questo modo è possibile avere una visione di insieme del processo di registrazione. Come si può notare i nomi dei processi sono stati  scelti in modo da poter essere auto esplicativi nella comprensione di tale meccanismo. Unica nota che occorre fare è relativa agli ultimi step del processo, di seguito approfonditi.

<h4 id="MailService">Mail Service</h4>

Come si può ben notare negli ultimi passi del processo di registrazione viene fatto riferimento a due classi particolari, *EmailSender* e *JavaMailSender*, vediamole nel dettaglio. Tali classi forniscono l'implementazione Java di servizi di invio messaggi via mail, nel file *pom.xml* troviamo: 

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

Le interfacce le classi per tale supporto sono organizzate in questo modo:

- **EmailSender interface**: è l'interfaccia di primo livello che integra le funzionalità base per l'invio di email. 
- **JavaMailSender interface**: è la subinterface di *MailSender*. Supporta MIME messages ed è usata in congiunzione con la classe *MimeMessageHelper* per la creazione di un *MimeMessage*.
- **MailSender class** è l'implementazione dell'interfaccia  *JavaMailSender*.
- **SimpleMailMessage class**: viene utilizzata per creare email usuali, con campi di to, from, cc, oggetto e testo.
- **MimeMessageHelper class**: è una classe di supporto per la creazione di  MIME messages, in particolar modo per l'inserimento di immagini come allegato e per l'inserimento di testo in formato html.

Come accennato precedentemente, Spring Boot permette la configurazione di tali dependencies in un apposito file di properties chiamato *application.properties*, in esso troviamo:

```ini
# For Google Mail Server
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=residenza.elis.bacheca@gmail.com
spring.mail.password=elis1928
spring.mail.properties.mail.smtp.ssl.trust=*
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.smtp.ssl.protocols=TLSv1.2
```

Quelle riportate sono le properties per configurare il server SMTP Gmail. Quello che resta da fare è dunque fornire l'implementazione dell'interfaccia *EmailSender*:

```java
@EnableAsync
@Service
public class EmailService implements EmailSender {
	
	// SetUp Logger for logging email sending operation
	private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	
	// List all service to use
	private final JavaMailSender javaMailsender;
	
	public EmailService(JavaMailSender javaMailsender) {
		super();
		
		this.javaMailsender = javaMailsender;
	}

```

E effettuare l'override del metodo che conterrà la composizione del messaggio a nostro piacimento:

```java
// Send Email
@Override
@Async
public void send(String to, String email) {

    if (to == null || email == null || to.isBlank() || email.isBlank()) {
        throw new IllegalArgumentException();

    }

    try {
        // Util class to setup mail sender
        MimeMessage mimeMessage = javaMailsender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        // Fill the mail
        helper.setText(email, true);
        helper.setTo(to);
        helper.setSubject("Bacheca UniCollege");
        helper.setFrom("residenza.elis.bacheca@gmail.com");

        // Send it
        javaMailsender.send(mimeMessage);


    } catch (Exception e) {

        // Else logged the err and then trown a new IllegalStateEx
        LOGGER.error("Failed to send the email", e);
        throw new IllegalStateException("Failed to send the email");		
    }

}
```

Da notare che il metodo è stato etichettato con l'annotation @Async, in questo modo l'invio delle mail non rallenterà la web app e dunque la user experience.

<h3 id="Web"> Web </h3>

<h4 id="SpringMVC">Spring MVC</h4>
<div style="text-align: justify">
    Spring MVC è il framework di Spring per lo sviluppo di web app. Proprio come suggerisce il suo nome segue il design pattern MVC e fa uso del <i>DispatcherServlet</i>. Il <i>DispatcherServlet</i> è una classe che agisce da front controller nell'architettura web, essa riceve le richieste in entrata, converte i payload delle richieste con le rappresentazioni dei dati interne alla web app, manda le rappresentazioni dei dati ai model per successivi riprocessamenti e infine effettua il rendering delle view con i dati processati.
</div>



![The-Spring-MVC-architecture](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\The-Spring-MVC-architecture.png)

Più precisamente:

- Il *model* layer si occupa delle strutture dati e della business logic della web app implementata nelle entities, nei repository e nei services.
- Il *controller* layer si occupa di legare dati e template in modo che possano essere poi elaborati dal *view resolver*. 
- Le *view* forniscono la rappresentazione grafica finale dei dati attraverso cui lo user può interagire.  

<img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\SpringMVC.webp" alt="SpringMVC"/>

Ancora una volta, tale integrazione è possibile grazie alla seguente Spring Boot dependencies, esplicitata all'interno del file *pom.xml*:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

Le uniche configurazioni effettuate all'interno del file *application.properties* sono le seguenti:

```ini
#Configuration for conversion of date and time
spring.mvc.format.date=yyyy-MM-dd
spring.mvc.format.time=HH:mm:ss
```

In questo modo la web app utilizza una rappresentazione univoca globale di conversione delle date e delle ore, evitando così problemi di compatibilità.

Un altro settaggio fine effettuato è il seguente:

```java
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	// Configuration to expose filesystem directory where to store the imgs of the
	// activity
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // reference dir name
		registry.addResourceHandler("/activity-photos/**") 
            // real path dir reference
				.addResourceLocations("file:/tmp/activity-photos/"); 

	}

}
```
<div style="text-align: justify">
    E' stata inserita una directory nel file system dove effettuare lo store delle immagini della bacheca ed è stata registrata come custom registry resource location. In questo modo Spring MVC può caricare da questa location le risorse e comporre correttamente le views. Notasi che la classe è stata etichettata con l'annotation <i>@Configuration</i>, in questo modo Spring Boot è in grado di riconoscere la configurazione ed effettuarne il bind con l'env. globale. Tale configurazione poteva essere fatta anche tramite file <i>application.properties</i> ma per maggiore chiarezza si è preferito procedere in maniera classica.
</div>


<div style="text-align: justify">
A questo punto possiamo passere al layer Controller, esso viene sviluppato all'interno del package <i>com.vincenzomariacalandra.provaFinale.BachecaUniCollege.controller</i>. Ogni classe al suo interno è una classe controller che mappa un apposito template contenuto all'interno della cartella <i>/BachecaUniCollege/src/main/resources/templates</i>. Per maggior rigore si è scelto chiamare di chiamare ogni template con lo stesso nome della corrispettiva classe controller.
</div>

Ogni classe controller ha dunque la seguente struttura:

```java
@Controller
@RequestMapping("/homeBacheca")
public class HomeBachecaController {
	
	// All Services required
	private final ActivityService activityService;
	
	@Autowired
	public HomeBachecaController(ActivityService activityService) {
		this.activityService = activityService;
	}
	
	// Initialization of homePage 
	@GetMapping
	public String listAllActivities(Model model) {
		
		//Add list attribute to the model
		ArrayList<Activity> list = new ArrayList<>();
		activityService.getActivitiesApproved().iterator().forEachRemaining(list::add);
		model.addAttribute("activities", list);
		
		return "homeBacheca";
	}
	
}
```

- L'annotation *@Controller* serve a mappare la classe all'interno di Spring come custom controller.
- L'annotation @RequestMapping specifica l'endpoint da mappare.
- L'annotation @GetMapping specifica il metodo da chiamare quando viene effettuata una GET.
- L'annotation @PostMapping specifica il metodo da chiamare quando viene effettuata una POST.
- In alternativa alle annotation @Get.. e @Post è possibile usare *@RequestMapping(path = "/path", method = RequestMethod.GET)* prima di ogni metodo.
- Ogni metodo del controller ritorna sempre una stringa che rappresenta il template html corrispondente o un altro endpoint.
- E' possibile specificare che un endpoint richiede dei parametri nella URL tramite *@RequestParam("")*

```java
// Update Activity handler
@RequestMapping(path = "/updateTertulia", method = RequestMethod.GET)
public String updateShowFormTertuliaATema(@RequestParam("id") Long id, ...)
```

- E' possibile specificare degli attributi di modello tramite l'annotatio *@ModelAttribute*

```java
// Update activity post handler
@RequestMapping(path = "/updateTertulia", method = RequestMethod.POST)
public String updateTertuliaATema(@ModelAttribute Activity activity, ... )
```

Inoltre tutti gli endpoint sono stati documentati meglio all'interno della relative classi.

<h4 id="Templating">Templating</h4>

Come motore di templating dinamico delle view è stato scelto **Thymeleaf**. Il punto di forza principale di Thymeleaf è quello di usare un linguaggio di template naturale. 

Con le dependencies per SpringBoot sono possibili diverse integrazioni, Thymeleaf risulta ideale per il moderno sviluppo web HTML5 JVM e in particolar modo per essere integrato con <i>Spring MVC</i>. 

```xml
<!-- Framework to template dinamically html files -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity5</artifactId>
</dependency>
```

Le uniche configurazioni apportate sono di utilities e non introducono sostanziali implementazioni, inoltre sono sono auto esplicative:

```ini
# Thymeleaf settings for templatins HTML files
spring.thymeleaf.enabled=true 
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
```

Per attivare l'integrazione occorre importare i namespace nel seguente modo:

```html
<!doctype html>
<html lang="en" 
    xmlns:th="http://www.thymeleaf.org"
	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity5">
<head>
    ...
```

Tra le integrazioni più importanti vi è quella della security. Tali dependencies introducono gli <i>Spring Security Dialect</i> che ci permettono di  mostrare i contenuti all'interno della view in base al ROLE dell'utente, ai sui permessi o ad altre espressioni di sicurezza specifiche di Spring.

Un banale utilizzo che ne è stato fatto nel progetto è il seguente:

```html
<li th:if="${#authentication.getPrincipal().isDirettore()}" class=" nav-item"><a class="nav-link" href="/gestioneAttivita">Gestione Attività</a></li>
```

- Il tag <i>th:if</i> permette l'introduzione di una condizione da valutare prima di renderizzare il \<div>.
- La stringa <i>"${...}"</i> comunica al tag che il contenuto della stringa è una variabile.
- L'oggetto identificato con <i>#authentication</i> viene introdotto con <i>thymeleaf-extras-springsecurity5</i> e rappresenta lo Spring Security authentication object.

Altri tag utilizzati sono stati:

- <i>th:text=""</i> permette la sostituzione del testo "Example..." con quello contenuto all'interno della variabile <i>"${msg}"</i>.

```html
<p th:text="${msg}">Example text for confirmation page</p>
```

- <i>th:href=""</i> permette l'inserimento di hyperlink e <i>@{...}</i> suggerisce che la stringa venga valutata come URL

```html
<a th:href="@{/dettaglioAttivita?id=}+${activity.id}" class="btn btn-primary">Dettaglio Attività</a>
```

Per una totale comprensione dei tag introdotti si rimanda alla documentazione ufficiale al seguente [link](https://www.thymeleaf.org/doc/tutorials/2.1/usingthymeleaf.html) e alla repository di <i>[thymeleaf-extras-springsecurity5](https://github.com/thymeleaf/thymeleaf-extras-springsecurity)</i>.

<h4 id="SalvataggioImmagini">Salvataggio immagini</h4>

<div style="text-align: justify">
Come già visto in precedenza si è provvisto a creare un apposita configurazione per permettere alla classe <i>NuovaAttivitaController</i> di salvare i file img in un apposita directory nel file system e successivamente permettere così alle view di poterle renderizzare. 
</div>
Il processo di salvataggio delle immagini avviene in 3 fasi:

- Caricamento del file tramite apposito field nel form di creazione attività. 

<img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\NuovaAttivita.PNG" style="zoom:50%;" />

```html
<form action="#" th:action="@{/nuovaAttivita}" th:object="${activity}" method="post"
	enctype="multipart/form-data">
...
...
	<div class="form-group">
    	<label for="fileImage">Foto per bacheca</label> 
    	<input class="form-control" id="fileImage" name="fileImage" type="file" 					accept="image/png, image/jpeg" required></input>
	</div>
...
```

Viene specificato che l'encoding sarà di tipo *multipart* e *form-data* e che il file caricato dovrà essere con estensione *png* o *jpg*.

- Il file viene recepito dall'apposito controller che procede alla validazione dell'activity.

```java
@RequestMapping(path = "/nuovaAttivita", method = RequestMethod.POST)
public String addActivity (@Valid @ModelAttribute("activity") Activity activity, @RequestParam(name = "fileImage", required = false) MultipartFile multipartFile, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes)
    throws IOException { 
    
    if (multipartFile != null) {

        //Error checking business constraints
        String err = processing.multipartProcessing(multipartFile, activity);
	...
```

- *multipartProcessing(...)* è un metodo di utility che gestisce il salvataggio delle imgs nel file system e viene implementato nella classe *MultipartProcessingUtility*, vediamolo nel dettaglio.

```java
@Component
public class MultipartProcessingUtility {
	
	// All Services required
	private final ActivityService activityService;
	
	private static String ROOT_UPLOAD_DIR = "/tmp/activity-photos/";
	
	@Autowired
	public MultipartProcessingUtility(ActivityService activityService) {
		this.activityService = activityService;
	}
	
	public String multipartProcessing(MultipartFile multipartFile, Activity activity) throws IOException {
		
		// Generation of filename
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
               
        
        // Insertion of filename in Acticity entity
        activity.setPhoto(fileName);
    
    
    	// Saving Activity in DB
    	String err = activityService.addNewActivity(activity);
    	
    	if (err != null) {
    		
        	System.out.println(err);
        	
        	return err;
    	}
    	
    	// Checks if ROOT_UPLOAD_DIR exist
    	if (!Files.exists(Paths.get(ROOT_UPLOAD_DIR))) {
    		Files.createDirectory(Paths.get(ROOT_UPLOAD_DIR));
    	}

        // Generation of path to the directory where to store the photo 
        String uploadDir = ROOT_UPLOAD_DIR + activity.getId();
        
        Path uploadPath = Paths.get(uploadDir);
        
        // Checks if folder exist
        if (!Files.exists(uploadPath)) {
        	Files.createDirectory(uploadPath);
        }
        
        // Saving file in the correct dir
        try (InputStream inputStream = multipartFile.getInputStream()) {
        	
        	Path filePath = uploadPath.resolve(fileName);
        	
        	Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		
        } catch (IOException ioe) {
			throw new IOException("Could not save the file!");
		}
        
        return null;
		
	}

}
```
<ul>
    <ul>
        <li>In primis viene generato il nome del file a partire dall'originale.</li>
        <li>A questo punto viene salvato il nome del file nella entity che rappresenta tale activity.</li>
        <li>Viene controllata se la root dir dove varranno salvate le immagini esiste già e viene creata se non esiste.</li>
        <li>Viene generato il path della dir dove effettuare lo store del file img e viene creata la dir se non esiste.</li>
        <li>A questo punto viene scritto il file img all'interno della dir aprendo uno stream dati.</li>
    </ul>
</ul>
Le configurazione apportate per la gestione delle richieste <i>HTTP multipart</i> sono le seguenti:

```ini
#MultiPartFile COnfiguration to store Images
spring.servlet.multipart.max-file-size=50MB
spring.servlet.multipart.max-request-size=50MB
spring.servlet.multipart.enabled=true
```

<h3 id="Testing">Testing</h3>
<div style="text-align: justify">
Il testing è stato un punto chiave nello sviluppo della web app, infatti si è evoluto con essa. In origine, quando ancora l'interfaccia grafica non era stata sviluppata e l'app comunica con l'esterno tramite un RESTController, la metodologia di testing principale è stata relegata all'utilizzo di software come Postman.
</div>


<div style="text-align: justify">
Postman è una piattaforma API per la costruzione e l'utilizzo di API. Postman semplifica ogni fase del ciclo di vita delle API e snellisce la collaborazione in modo da poter creare API migliori, più velocemente. 
</div>


![Postman](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\Postman.PNG)

In questo modo si è potuto si da subito constatare la presenza di errori nello sviluppo della logica di business.

<div style="text-align: justify">
Congiuntamente a quanto riportato sopra si è proceduto allo sviluppo di apposite unità di testing per le singole classi che compongo la logica di business, tutte contenute all'interno del package <i>com.vincenzomariacalandra.provaFinale.BachecaUniCollege.service</i>. Tali unità di testing fanno uso di <i>JUnit5 e Mockito</i> piuttosto che utilizzare il supporto nativo offerto da <i>SpringBoot starter test</i>. Tale scelta è  motivata dal fatto che SpringBoot appesantisce di molto l'elaborazione degli unit test da pochi millisecondi a decine di secondi ostacolando così il flusso <i><b>test -> sviluppo -> test</b></i> promosso dalla logica <i><b> Test Driven Development, TDD</b></i>.
</div>
Per fare ciò è stato necessario effettuare un esclusione di particolari librerie e import esterne di altre, di seguito quanto detto:

```xml
<!-- For Testing purpose -->
<!-- exclude junit 4 and Mockito-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
    <exclusions>
        <exclusion>
            <groupId>org.junit</groupId>
            <artifactId>junit</artifactId>
        </exclusion>
        <exclusion>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
        </exclusion>
        <exclusion>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-all</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<!-- junit 5 -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>${junit-jupiter.version}</version>
    <scope>test</scope>
</dependency>

<!-- Mockito dependecy -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>2.21.0</version>
</dependency>

<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <version>2.23.0</version>
    <scope>test</scope>
</dependency>

<!-- Old vintage -->
<dependency>
    <groupId>org.junit.platform</groupId>
    <artifactId>junit-platform-runner</artifactId>
    <version>1.2.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit.vintage</groupId>
    <artifactId>junit-vintage-engine</artifactId>
    <version>5.2.0</version>
    <scope>test</scope>
</dependency>
```

Le classi di unit test hanno tutte una struttura così fatta:

```java
@ExtendWith(MockitoExtension.class)
public class ActivityServiceImplUnitTest {
    
    @Mock
	private ActivityRepository activityRepository;

    @Mock
	private UserService userService;

    @Mock
	private EmailSender emailSender;

    private ActivityService service;
    
    @BeforeEach
    public void setUp() {
    	
    	service = new ActivityService(activityRepository, emailSender, userService);
    	
    }

	@Test
	public void contextLoads() throws Exception {
		assertNotNull(service);
	}
...
```

Il nome della classe fa riferimento alla classe "bersaglio" dello unit test.

La classe é etichettata con l'annotation <i>@ExtendWith(MockitoExtension.class)</i>, utilizzata per abilitare Mockito, e opzionalmente con l'annotation <i>@RunWith(JUnitPlatform.class)</i> utilizzata per runnare la classe di unit test singolarmente.

Ogni attributo della classe di test etichettato con l'annotation <i>@Mock</i> verrà fatto diventare una classe fantoccio che restituirà <i>null</i> ogni volta che verrà chiamato un suo metodo dalla classe che subisce il test salvo particolari casi in cui chi scrive il test richiede che tale comportamento venga sovrascritto da un altro.

Il metodo etichetta con l'annotation <i>@BeforeEach</i> verrà chiamato prima di ogni metodo etichetta con l'annotation <i>@Test</i>, questi ultimi contengono appunto i test che verranno svolti sul metodo della classe bersaglio.

 ```java
 @Test
 public void deleteActivityTest() {
 
     Random rand = new Random();
     Long id = rand.nextLong();
 
     Activity activity = new Activity();
     Optional<Activity> optional = Optional.of(activity);
 
     reset(activityRepository);
 
     lenient().when(activityRepository.findById(id)).thenReturn(optional);
 
     assertNull(service.deleteActivity(id));
     verify(activityRepository, VerificationModeFactory.times(1)).findById(id);
 
     reset(activityRepository);
     lenient().when(activityRepository.findById(null)).thenReturn(optional);
     assertEquals("Activity id could not be null!", service.deleteActivity(null));
 }
 ```

I punti salienti sono i seguenti:

- Il metodo <i>reset(activityRepository)</i> si occupa resettare il mock da eventuali override di comportamenti precedenti e di azzerarne il numero di istanze.
- Il metodo <i>lenient().when(activityRepository.findById(id)).thenReturn(optional);</i> si occupa di fare appunto l'override del comportamento del metodo findById(...) della classe fantoccio.
- Infine, ma non per ultimo, il metodo <i>assertNull(service.deleteActivity(id));</i> si occupa di effettuare il test sul metodo della classe bersaglio, in questo caso verifica se ritorna valore nullo.
- Il metodo <i>verify(activityRepository, VerificationModeFactory.times(1)).findById(id);</i> verifica che il metodo sia stato chiamato correttamente.

I risultati ottenuti grazie a questa strategia di testing sono valutabili in termini di velocità di verifica e destrezza, infatti hanno permesso di velocizzare lo sviluppo e la risoluzione di errori prima che l'utente finale possa averli potuti sperimentare durante l'utilizzo del prodotto causando così disagio e insoddisfazione. 

Di seguito i risultati ottenuti:

![TestingJUnit](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\TestingJUnit.PNG)

Proseguendo, implementando l'interfaccia grafica è stato possibile effettuare i primi test di **<i>workflow</i>**, ovvero:

- Testare il flusso di lavoro end-to-end che portano l'utente attraverso una serie di pagine web da completare.
- Testare gli scenari negativi, in modo che quando un utente esegue  un passo inaspettato nella vostra web app venga mostrato un  messaggio di errore o un aiuto appropriato.

La conclusione di tale test ha portato alla conclusione che il prodotto ottenuto rispecchia le richieste dei futuri utilizzatori.

Si è poi passati ai test di **usabilità**. I test di usabilità sono una parte vitale di qualsiasi  progetto web. Può essere effettuato da testers o da un piccolo gruppo di utenti facenti parte del target finale dell'applicazione web. 

- Testata la navigazione del sito: Menu, pulsanti o link a diverse pagine del tuo sito dovrebbero essere facilmente visibili e coerenti su tutte le pagine web.
-  Testato il contenuto: Il contenuto dovrebbe essere leggibile senza errori di ortografia o grammatica.    Le immagini, se presenti, dovrebbero contenere un testo "alt".

Il risultato di tale test, effettuato su 1/3 della popolazione finale che utilizzerà l'app, ha permesso la risoluzione di svariati problemi inosservabili all'occhio dello sviluppatore e migliorando così la qualità del prodotto finale.

Continuando, si è passati ai test di **compatibilità**. I test di compatibilità assicurano che la web app venga visualizzata correttamente su diversi dispositivi. Questo include: 

- Test di compatibilità del browser: la stessa pagina in diversi browser venga visualizzata in ugual modo.  
- Test di compatibilità con i browser mobili. Il rendering del layout è differente da dispositivo a dispositivo.

Di seguito i risultati ottenuti:

- Explorer

<img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\Explorer.PNG" alt="Explorer" style="zoom:50%;" />

- Edge

<img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\Edge.PNG" alt="Edge" style="zoom:50%;" />

- Firefox

<img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\Firefox.png" alt="Firefox" style="zoom:50%;" />



- Android - Browser Opera

<img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\photo5883970862284127955.jpg" alt="photo5883970862284127955" style="zoom:50%;" />

I risultati sono il linea rispetto a quanto ci si aspettava, minime differenze tra i browser sono dovute alle differenti condizioni di zoom di default.

Infine sono stati effettuati dei test sull'infrastruttura cloud per testarne la resistenza minima ad attacchi di tipo *DDoS*. Il cloud provider, *OVH*, ha immediatamente provvisto a deviare il traffico sull'infrastruttura di mitigazione notificando l'utente dell'avvenuto attacco.

![DDoS](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\DDoS.PNG)

<h3 id="DeployEInstallazione">Installazione e Deploy</h3>

<h4 id="Maven">Maven</h4>

Per prima cosa occorre effettuare la generazione del file *.jar*, per fare ciò:

- E'  possibile aprire il progetto con un IDE che abbia l'estensione per *Maven* installa, fare tasto destro sul progetto e cliccare su *maven install*.

<img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\MavenInstall.png" alt="MavenInstall" style="zoom:50%;" />

- Eseguire direttamente il comando da terminale all'interno della root directory del progetto.

  ```bash
  mvn install
  ```

Verrà generato il file *.jar* all'interno della cartella *target*.

<h4 id="Docker">Docker</h4>

Come già accennato in precedenza è stato scelto di utilizzare la containerizzazione come metodologia di deploy. Per fare ciò è stato definito un Dockerfile contenente tutte le informazioni necessarie pe costruire la nostra immagine.

```yaml
FROM openjdk:11
COPY target/BachecaUniCollege-0.0.1-SNAPSHOT.jar BachecaUniCollege.jar
EXPOSE 8080
VOLUME ["/tmp/activity-photos", "/var/log"]
ENTRYPOINT ["java","-jar","/BachecaUniCollege.jar"]
```

Vediamolo nel dettaglio:

- Il primo comando invoca il download dell'immagine *openjdk:11*, tale immagine è predisposta per ospitare applicativi JAVA11.
- Il secondo comando copia il file *.jar* generato all'interno del container.
- Il terzo comando espone la porta 8080 del container.
- Il quarto comando dichiara i volumi che verranno appesi al container.
- Il quinto comando contiene la sequenza dei comanda da eseguire per avviare il file *.jar*. 

A questo punto non ci resta che scrivere il *docker-compose.yaml*:

```yaml
version: '3'
services:
  bacheca-uni-college:
    build:
      context: .
      dockerfile: Dockerfile
    image: bacheca-uni-college:latest
    volumes:
      - BachecaUniCollege-data:/tmp/activity-photos
      - BachecaUniCollege-log:/var/log
    ports:
      - "80:8080"
    networks:
      - bacheca-uni-college-mysqldb-network
    depends_on:
      - mysqldb

  mysqldb:
    image: mysql:8
    networks:
      - bacheca-uni-college-mysqldb-network
    environment:
      - MYSQL_ROOT_PASSWORD=root
      - MYSQL_DATABASE=database

volumes:
  BachecaUniCollege-data:
    driver: local
    driver_opts:
      o: bind
      type: none
      device: /tmp
  BachecaUniCollege-log:
    driver: local
    driver_opts:
      o: bind
      type: none
      device: /var/log/BachecaUniCollege

networks:
  bacheca-uni-college-mysqldb-network: null
```

Come possiamo ben vedere abbiamo 3 layers di definizione:

- Il primo layer *services* contiene i servizi veri e propri della nostra web app, ovvero il nostro applicativo SpringBoot e il database MySQL.

  - ```yaml
        bacheca-uni-college:
          build:
            context: .
            dockerfile: Dockerfile
          image: bacheca-uni-college:latest
          volumes:
            - BachecaUniCollege-data:/tmp/activity-photos
            - BachecaUniCollege-log:/var/log
          ports:
            - "80:8080"
          networks:
          - bacheca-uni-college-mysqldb-network
        depends_on:
          - mysqldb
    ```
    
    Soffermandosi brevemente su tale definizione, sottolineamo alcune parti salienti:
    
    - *build: .* indica che l'immagine verrà creata con il Dockerfile.
    - *volumes:* dichiara che le dirs l'elencate saranno legate ai volumi dichiarati sotto.
    - *ports: 80:8080* mappa la porta 80 del server con la porta 8080 del container in modo che sia raggiungibile dall'esterno.
    - *networks:* contiene una lista di reti attraverso cui il container potrà comunicare.
    - *depends_on*: dichiara che tale service dipende dal *mysqldb*, dunque prima di essere avviato dovrà attendere le tutte le dipendenze siano risolte.

- Il secondo layer contiene i volumi che verranno condivisi con il container che contiene la SpringBoot app, in particolare:

  - *BachecaUniCollege-data* conterrà le immagini salvate nel filesystem.
  - *BachecaUniCollege-log* conterrà le informazioni di log generate dalla web app e dal cotainer. **Occorre che la cartella */var/log/BachecaUniCollege* esista già, altrimenti l'esecuzione fallira.** 

- Il terzo layer contiene la rete che connetterà i 2 services dichiarati sopra.

A questo punto non resta che effettuare il build e il run:

```bash
docker-compose up --build
```

Se si vuole che il processo venga eseguito come demone:

```bash
docker-compose up -d --build
```

Per stoppare il tutto:

```bash
docker-compose down
```


<h3 id="LoggingEMonitoring">Logging e Monitoring</h3>

<h4 id="Logging">Logging</h4>

Per quanto riguarda il logging Spring Boot Starter integra *Apache Commons Logging* e *Logback*, inoltre implementa un interfaccia che standardizza il loro utilizzo, *slf4j o Simple Logging Facade for Java*. 

Un esempio di utilizzo lo troviamo nell'*EmailService*:

```java
@EnableAsync
@Service
public class EmailService implements EmailSender {
	
	// SetUp Logger for logging email sending operation
	private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	...
        
	// Send Email
	@Override
	@Async
	public void send(String to, String email) {
		
		try {
			// Util class to setup mail sender
            ...
			
		} catch (Exception e) {
			
			// Else logged the err and then trown a new IllegalStateEx
			LOGGER.error("Failed to send the email", e);
			throw new IllegalStateException("Failed to send the email");		
		}
		
	}
    ...
```

- Il logger viene istanziato con riferimento alla classe da monitorare con l'aiuto del *LoggerFactory*.
- Una volta istanziato è possibile selezionare il tipo di messaggio di logging, *es. Error*, assegnare un tag e un messaggio. 

<h4 id="Monitoring">Monitoring</h4>

Per quanto riguarda il monitoring è stato integrato *Spring Actuator*:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
</dependencies>
```

Spring Boot include una serie di funzioni aggiuntive per monitorare e gestire quando la web app viene messa in produzione. E' possibile monitorare l'applicazione usando gli endpoint HTTP alberati sotto */actuator/***.

La configurazione di seguito elencata espone tutti gli endpoint di Spring Actuator tranne quello per lo shutdown dell'applicativo:

```ini
# Configuration for Spring Actuator tool
management.endpoints.web.exposure.include=*
# if you'd like to expose shutdown:
# management.endpoint.shutdown.enabled=true
```

Per esempio un endpoint molto utile è */actuator/logfile* che ritorna il contenuto del file di log.

A proposito del file di log, è stato specificato un file di logging per la web app in un apposita cartella:

```ini
# Logging configurtion
logging.file.name=/var/log/BachecaUniCollege.log
server.tomcat.accesslog.enabled=true
```

Tale cartella è un volume denominato *BachecaUniCollege-log* e montato sul container docker. Tale volume punta al filesystem del server che lo ospita, in questo modo è sempre accessibile, vedi <a href="#DeployEInstallazione">qui</a>.   

<h3 id="ClassDiagram">Class Diagram</h3>

Di seguito viene riportato il class diagram del progetto per avere una visione complessiva di esso:

<img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\BachecaUniCollege.umlcd.png" style="zoom: 200%;" />

<h2 id="Repository">Repository</h2>

https://github.com/Vincenzo-Maria-Caladra/Prova-Finale-Ingegneria-del-Software.git
