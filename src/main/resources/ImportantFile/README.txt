
	PRIMA DI TUTTO:

	    CREARE UNA PRORPIO CONNESSIONE A DB E  MODIFICARE POI IL NOME DEL DB PASSWORD E USERNAME, NEL FILE CHE SI TROVA IN
	
	        resources --> application.properties

	SECONDO STEP:

        fatto questo lanciare gli script per creare le tabelle, le function, le stored-procedure che serviranno poi per testare le chiamate.
        importante seguire questo ordine perchè come nelle classi java in questo caso le tabelle sono il fulcro o punto di partenza, le funzioni serviranno poi alle stored-procedure
        basandosi, ovviamente su dei campi specifici delle tabelle create precedentemente ed infine le stored-procedure che avranno al loro interno una o più funzioni;
        OVVIAMENTE IL TUTTO ALL'INTERNO DI ORACLE SQLDEVELOPER...... nel caso non si ha in locale vi è una guida esplicativa in:

            resources
                Important-File
                    SqlDeveloper

    TERZO STEP:

        3.1

            insallare PostMan per creare le varie chiamate che siano esse POST, PUT, GET, DELETE  ecc... cliccando sul tasto "Import", tasto collocato in alto a sinistra accanto alla
            keyword "New", apparirà una schermata già impostata per ricevere il "FILE" di importazione delle collections, quindi cliccare sul tasto "Upload Files" e selezionare la collection
            presente all'interno del progetto, situato in:

                resources
                    ImportantFile
                        collection-postman

            ed infine cliccare su "Import", dopo il caricamento apparirà una una coolezzione denominata "Products AND Shops" dove all'interno vi saranno delle chiamate da testare

        3.2

            installare Git, sistema di versionamento veloce ed efficace da utilizzare anche tramite Injtellij per staccare barnch o rami di lavoro, pushare modifiche o pullarle ed eventualmete
            revertare nel caso di errori, all'interno del progetto in:

                resources
                    Important-File
                        Git
            vi sarà una breve spiegazione con un link ufficiale di documentazione per i comandi e il funzioanmento nello specifico.


    QUARTO STEP:

        entrare nella folder o directory nella quale si desidera clonare il progetto, tramite il seguente link:

           https://github.com/MarcoCaccia1992/MyBatis-example.git

        e, SOLO DOPO AVER INSTALLATO GIT, aprire il teminale dalla directory, masta poi scrivere "cmd" nella barra della ricerca ed esso si aprirà nel path specifico della folder,
        e lanciare il comando:

            -- git init -- (I DOPPO TRATTINI O DOUBLE SCORE, NON VANNO COPIATI)

        ovvero gli si sta dicendo a Git di considerare quella folder come un punto di inizio della vostra repository Git locale, fatto ciò si dovrà
        lanciare il seguente comando:

            -- git clone "https://github.com/MarcoCaccia1992/MyBatis-example.git" -- (I DOPPO TRATTINI O DOUBLE SCORE, NON VANNO COPIATI)

        ed ora Git scaricherà il progetto intero in locale.
        Una volta fatto questo si potrà, dall'ide utilizzato in questo caso intellij lavorare sul progetto

    QUINTO STEP:

        ora come ultimo step, staccare un branch ovvero, il seguente comando

           -- git checkout -b "nome_branch" --

        il nome del branch sarebbe duopo che avesse una naming-convention ovvero di solito si mette numero di ticket/incident/Jira + nome dello sviluppatore + data, dopo di che
        effettuare un avvio completo dell'applicativo per permettere la creazione di una tabella di appoggio, creata dall'annotation ManyToMany per permettere questo tipo di link fra tabelle.


    TROUBLE SHOOTING

        NEL CASO VI SIA UN ERRORE DA GIT, sarà sicuramente un errore di connessione dei branch e nel caso seguire step per step i comandi che git in automatico darà da lanciare
        seguendo il loro ordine.

        in caso di impossibiltà di PUSH eseguire questi comandi ignorando il primo qual'ora si sia già creato il proprio branch di lavoro

        git branch -m develop <BRANCH>
        git fetch origin
        git branch -u origin/<BRANCH> <BRANCH>
        git remote set-head origin -a


	