<h1 style="text-align: center"> Documentazione "Bacheca UniCollege" </h1>

<h3 style="text-align: center">Prova finale Ingegneria del Software</h3>

<h4 style="text-align: center">Autore: Calandra Vincenzo Maria</h4>



## Contesto

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

## Soluzione

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

## Tecnologie

<div style="text-align: justify">
Per il conseguimento di tali obiettivi è stato scelto come linguaggio di programmazione JAVA 11, ad oggi esiste un numero notevole di applicazioni e siti web che fanno uso di questo linguaggio. <br><br>Infatti JAVA  è un linguaggio ad alto livello orientato agli ogetti con una forte tipizzazione statica che si appoggia sulla omonima piattaforma, JVM, per questo motivo JAVA, che è linguaggio sia compilato che interpretato, riesce a girare su un numero considerevole di dispositivi differenti con prestazioni mediocri in termini di tempo di esecuzione e prestazioni.<br><br>
Grazie a queste sue caratteristiche JAVA, nelle sue varie versioni, ha creato intorno a se nel corso degli anni un forte interesse da parte delle comunity di sviluppatori che hanno creato innumerevoli librerie e framework per questo linguaggio.<br><br>
A tal proposito si è scelto di adottare Spring Boot come framework per lo sviluppo web della applicativo. Spring Boot è un evoluzione del già noto Spring, il framework di JAVA più utilizzato. Spring permette di programmare in maniera facile, veloce e sicura grazie ad una serie di librerie native che implementano le best practice per il soddisfacimento di vari use-case in termini di sicurezza e performance in grado di collabborare tra loro permettendo così allo sviluppatore di poter concentrare i propri sforzi non sulla tecnologia ma sulla logica di busisness che dovrà animare l'appliccativo sviluppato. <br><br>Spring Boot introduce un livello di astrazione ancora superiore, esso infatti implementa una gestione facilitata delle configurazioni delle singole librerie del framework grazie ad un file di configurazione globale e una gestione automatica delle compatibilità delle versioni tra le varie librerie del framework. L'uso commerciale di Spring è regolato dalla licenza Apache 2.0.


<div style="text-align: justify">
    Per quanto riguarda la gestione del processo di build del progetto è stato scelto Maven. Maven è un framework dichiarativo di gestione del progetto che segue specifiche convenzioni per quanto riguarda la struttura dello stesso. Maven fa il build di un progetto usando il suo Project Object Model (POM) file e un insieme di plugin. Una volta che si familiarizza con un progetto Maven si è in grado di conoscere qualsiasi progetto Maven e ciò fa risparmiare molto tempo allo sviluppatore. In questo progetto sono state utilizzate principalmente le funzioni di gestione delle dependencies usate nel progetto, di build, di unit testing report e coverage. 
</div>

<div style="text-align: justify">
    Per quanto riguarda la persistenza dei dati la scelta è ricaduta su una database SQL di largo utilizzo, MySQL. Tale tecnologia permette alte prestazioni e scalabilità su Online Transaction Processing (OLTP) application ed è transaction safe, in quanto supporta pienamente operazioni ACID. Inoltre è molto semplice da utilizzare ed è distribuito in una versione Community con licenza GPL, General Public License. <br> <br>I software che vengono distribuiti con tale licenza possono essere utilizzati per tutti gli scopi, inclusi scopi commerciali e persino come strumento per la creazione di software proprietario.
</div>

<div style="text-align: justify">
	Infine si è scelto di effettuare il deploy dell'applicativo utilizzando la containerizzazione piuttosto che la virtualizzazione. La conteinerizzazione permette maggiore scalabilità e gestione delle risorse rispetto alla virtualizzazione, in questo modo ogni applicativo viene eseguito in processi che consumano il minimo delle risorse e che sono isolati tra loro. Inoltre la conteinerizzazione permette di impacchettare il software con tutte le sue dependencies e distribuirlo da un computing env ad un altro senza alcun problema di compatibilità. L'implementazione scelta, nonchè la più famosa e quella su cui si è poi basato lo standard di mercato,"containerd" della CNCF, è Docker. Docker Engine ha un piano di utilizzo gratuito ad uso personale o commerciale regolato dalla licenza Apache 2.0. </div>

## Base di dati

Di seguito il database schema e il diagramma ER.

![RE DB schematic](C:\Users\CalandraVM\Desktop\Prova Finale\RE DB schematic.PNG)



## Use Case

![Use Case Diagram](C:\Users\CalandraVM\Desktop\Prova Finale\Use Case Diagram.jpg)




## Dettaglio implementazioni tecnologiche
### Gestione Autenticazione e Autorizzazione

Le esigenze di procetto hanno rivelato la necessità dell'implementazione di un layer di security nella web application, in particolar modo è venuto fuori dalle analisi che:

- Occorre permettere l'accesso al portale solo ad utenti registrati, per tenere traccia dei fruitori del portale e per permettere future implementazioni correlate.
- Evitare che gli studenti abbiano accesso ad aree del portale riservate alla segreteria, alla direzione e ai tutor.

Per tale motivo si è deciso di utilizzare le librerie di Spring Security per l'implementazione di quanto citato sopra. 

Quando si lavora con Spring Boot, lo *spring-boot-starter-security* ingloba tutte le dependecies necessarie per l'implementazione di un layer di security all'interno dell'applicativo, come ad esempio *spring-security-core*, *spring-security-web*, e spring-security-config.

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

BCrypt negli ultimi anni è diventato uno standard nell'hashing delle password, è basato sull'agoritmo blowfish ed aggiunge un salt ed un fattore di costo all'hashing, in questo modo è resistente ad attacchi di tipo *rainbow table*. Inoltre è una funzione adattiva, cioè la sua complessità aumenta con il numero di iterazione, per tale motivo è anche resistente ad attacchi di tipo *brute force*. Da una serie di crypto-analisi è venuto fuori che BCrypt è molto più complesso da "crakkare" rispetto a SHA-512, in quanto quest'ultimo è messo a dura prova dal calcolo parallelizzato e dall'utilizzo di GPU sempre più potenti a differenza di BCrypt che non subisce tali fattori.

#### Autorizzazione

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

#### Autenticazione

Tutti gli altri accessi al portale sono vincolati alla sola autenticazione tramite /login.

```java
.anyRequest().authenticated()
.and().formLogin().loginPage("/login").defaultSuccessUrl("/homePage",true)
```

Da notare che l'ordine degli elementi contrassegnati da *antMatchers()* è significativo, le regole più specifiche sono poste prima di quelle generali. 

Di seguito sono riportate le configurazioni di logout:

```java
.and().logout()
.logoutSuccessUrl("/")
.logoutUrl("/perform_logout")
.invalidateHttpSession(true);
```

Da notare che sono stati cambiati tutti i valori di default degli endpoint, questa buona pressi evita che il framework sia facilmente identificabile dall'esterno, evitando così attacchi mirati.

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

In questo caso viene detto al provider che l'autenticazione va effettuata usando il bcryptPasswordEncoder dichiarato precedentemente e lo user *userService*, una classe che si occupa esclusivamente di interagire con la entity *AppUser* e che implementa l'interfaccia *UserDetailService* che richiede di implementare il seguente metodo:

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

#### Registrazione

Per quanto riguardo la registrazione si è adottato un meccanismo di verifica a 2 passaggi, di seguito sono elecanti i punti principali:

- Registrazione su apposita pagina raggiugibile tramite endpoint "/registration" o tramite opportuna page di login.

  <img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\RegistrationForm.PNG" alt="RegistrationForm" style="zoom:50%;" />

- Confema dell'avvenuta registrazione correttamete o incorrettamente.

  <img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\ErrorInRegistrationForm.PNG" alt="ErrorInRegistrationForm" style="zoom:50%;" />

  Or:

  <img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\ConfirmRegistration.PNG" alt="ConfirmRegistration" style="zoom:50%;" />

- Ricevimento della mail contenente il token nella url di conferma.

  ![ConfirmationEmail](C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\ConfirmationEmail.png)





- Messaggio di success:

  <img src="C:\Users\CalandraVM\Desktop\Prova Finale\Screen-BachecaUniCollege\ConfirmEmailMessage.PNG" alt="ConfirmEmailMessage" style="zoom:50%;" />

Il processo di registrazione segue dunque un percoso un po' più articolato rispetto all'autenticazione, per tale motivo si è provvisto a creare una rappresentazione grafica opportuna tramite sequece diagram.

![RegistrationSequenceiagram](C:\Users\CalandraVM\Desktop\Prova Finale\RegistrationSequenceiagram.jpg)





