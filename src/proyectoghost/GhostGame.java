/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoghost;

/**
 *
 * @author adrianaguilar
 */
// File: src/main/java/GhostGame.java
import java.util.Scanner;

public class GhostGame {
    private Player[] players;
    private Player currentPlayer;
    private String[][] board;
    private Scanner scanner;

    public GhostGame() {
        this.players = new Player[100]; 
        this.board = new String[6][6];
        this.scanner = new Scanner(System.in);
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                board[i][j] = " ";
            }
        }
    }

    public void login() {
        System.out.print("Ingrese su username: ");
        String username = scanner.nextLine();
        System.out.print("Ingrese su password: ");
        String password = scanner.nextLine();

        for (Player player : players) {
            if (player != null && player.getUsername().equals(username)) {
                if (player.validatePassword(password)) {
                    currentPlayer = player;
                    System.out.println("Login exitoso. Bienvenido " + username);
                    mainMenu();
                    return;
                } else {
                    System.out.println("Contraseña incorrecta.");
                    return;
                }
            }
        }
        System.out.println("Usuario no encontrado.");
    }

    public void createPlayer() {
        System.out.print("Ingrese un username único: ");
        String username = scanner.nextLine();
        System.out.print("Ingrese una contraseña (mínimo 8 caracteres): ");
        String password = scanner.nextLine();

        if (password.length() < 8) {
            System.out.println("Contraseña demasiado corta.");
            return;
        }

        for (Player player : players) {
            if (player != null && player.getUsername().equals(username)) {
                System.out.println("El username ya existe.");
                return;
            }
        }

        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = new Player(username, password);
                currentPlayer = players[i];
                System.out.println("Usuario creado exitosamente.");
                mainMenu();
                return;
            }
        }
        System.out.println("No se pudo crear el usuario. Intente nuevamente.");
    }

    
    ///////////////////////////////////////////////
    private void mainMenu() {
        int opcion;
        do {
            System.out.println("Menú Principal");
            System.out.println("1- Jugar Ghosts");
            System.out.println("2- Configuración");
            System.out.println("3- Reportes y Ranking");
            System.out.println("4- Mi Perfil");
            System.out.println("5- Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1 -> playGame();
                case 2 -> configureGame();
                case 3 -> generateReports();
                case 4 -> manageProfile();
                case 5 -> System.out.println("Saliendo al menú de inicio...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 5);
    }
    //////////
private void initializeGameBoard(Player player1, Player player2, boolean isRandom) {
    if (isRandom) {
        placeGhostsRandomly(player1, 0, 1); // aca se Coloca fantasmas de player1
        placeGhostsRandomly(player2, 4, 5); // aca Colocar fantasmas de player2
    } else {
        placeGhostsManually(player1, 0, 1); // aca Colocar fantasmas de player1
        placeGhostsManually(player2, 4, 5); // aca  se colocan fantasmas de player2
    }
}

private void placeGhostsRandomly(Player player, int rowStart, int rowEnd) {
    int goodCount = 4, badCount = 4;
    while (goodCount > 0 || badCount > 0) {
        int row = rowStart + (int) (Math.random() * (rowEnd - rowStart + 1));
        int col = (int) (Math.random() * 6);
        if (board[row][col].equals(" ")) {
            if (goodCount > 0) {
                board[row][col] = player.getUsername() + "_w"; // w es para fantasma bueno
                goodCount--;
            } else {
                board[row][col] = player.getUsername() + "_v"; // v  para fantasma malo
                badCount--;
            }
        }
    }
}

private void placeGhostsManually(Player player, int rowStart, int rowEnd) {
    int goodCount = 4, badCount = 4;
    System.out.println("Colocación manual para " + player.getUsername());
    while (goodCount > 0 || badCount > 0) {
        printBoard();
        System.out.println("Fantasmas restantes - Buenos: " + goodCount + ", Malos: " + badCount);
        System.out.print("Ingrese fila y columna (separados por espacio): ");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        scanner.nextLine(); 

        if (row < rowStart || row > rowEnd || col < 0 || col >= 6 || !board[row][col].equals(" ")) {
            System.out.println("Posición inválida. Intente de nuevo.");
            continue;
        }

        if (goodCount > 0) {
            board[row][col] = player.getUsername() + "_w";
            goodCount--;
        } else {
            board[row][col] = player.getUsername() + "_v";
            badCount--;
        }
    }
}


private boolean isValidSelection(Player player, int row, int col) {
    if (row < 0 || row >= 6 || col < 0 || col >= 6) return false;
    String cell = board[row][col];
    return cell.startsWith(player.getUsername());
}

private boolean isValidMove(int srcRow, int srcCol, int destRow, int destCol) {
    if (destRow < 0 || destRow >= 6 || destCol < 0 || destCol >= 6) return false;
    if (Math.abs(destRow - srcRow) + Math.abs(destCol - srcCol) != 1) return false;
    return !board[destRow][destCol].startsWith(currentPlayer.getUsername());
}

private void printBoard() {
    System.out.println("Tablero:");

    // aca  haciendo el diseno de las orillas de la tabla
    System.out.print("    "); // Espacio inicial para alinear filas con columnas
    for (int col = 0; col < 6; col++) {
        System.out.print(col + "   ");
    }
    System.out.println();

    // aca se Imprime tablero con números de fila
    for (int row = 0; row < 6; row++) {
        System.out.print(row + " | "); // este sera mi número de fila al inicio de cada línea
        for (int col = 0; col < 6; col++) {
            String cell = board[row][col];
            if (cell.equals(" ")) {
                System.out.print("    "); // Espacio vacío para diseno
            } else {
                System.out.print(cell.charAt(cell.length() - 1) + "   "); // Mostrar el tipo de fantasma
            }
        }
        System.out.println();
    }
}



private int goodGhosts = 4; //  NORMAL 
private int badGhosts = 4; 
private boolean isRandomMode = true; // Default

private void configureGame() {
    int option;
    do {
        System.out.println("Configuración del Juego");
        System.out.println("1- Dificultad");
        System.out.println("2- Modo de Juego");
        System.out.println("3- Regresar al Menú Principal");
        System.out.print("Seleccione una opción: ");
        option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                configureDifficulty();
                break;
            case 2:
                configureGameMode();
                break;
            case 3:
                System.out.println("Regresando al Menú Principal...");
                break;
            default:
                System.out.println("Opción no válida.");
        }
    } while (option != 3);
}


private void configureDifficulty() {
    System.out.println("Seleccione la dificultad:");
    System.out.println("1- Normal (4 buenos y 4 malos)");
    System.out.println("2- Expert (2 buenos y 2 malos)");
    System.out.println("3- Genius (2 buenos, 2 malos, 2 trampas adicionales)");
    System.out.print("Ingrese su opción: ");
    int difficulty = scanner.nextInt();
    scanner.nextLine(); 

    switch (difficulty) {
        case 1 -> {
            goodGhosts = 4;
            badGhosts = 4;
            System.out.println("Dificultad configurada a NORMAL.");
        }
        case 2 -> {
            goodGhosts = 2;
            badGhosts = 2;
            System.out.println("Dificultad configurada a EXPERT.");
        }
        case 3 -> {
            goodGhosts = 2;
            badGhosts = 2;
            System.out.println("Dificultad configurada a GENIUS.");
            System.out.println("Se agregarán 2 trampas adicionales por jugador.");
        }
        default -> System.out.println("Opción no válida. Manteniendo la configuración anterior.");
    }
}

private void configureGameMode() {
    System.out.println("Seleccione el modo de juego:");
    System.out.println("1- Aleatorio");
    System.out.println("2- Manual");
    System.out.print("Ingrese su opción: ");
    int mode = scanner.nextInt();
    scanner.nextLine(); 

    switch (mode) {
        case 1 -> {
            isRandomMode = true;
            System.out.println("Modo de juego configurado a ALEATORIO.");
        }
        case 2 -> {
            isRandomMode = false;
            System.out.println("Modo de juego configurado a MANUAL.");
        }
        default -> System.out.println("Opción no válida. Manteniendo el modo anterior.");
    }
}

private void initializeGameBoard(Player player1, Player player2) {
    if (isRandomMode) {
        placeGhostsRandomly(player1, 0, 1, goodGhosts, badGhosts);
        placeGhostsRandomly(player2, 4, 5, goodGhosts, badGhosts);
    } else {
        placeGhostsManually(player1, 0, 1, goodGhosts, badGhosts);
        placeGhostsManually(player2, 4, 5, goodGhosts, badGhosts);
    }
}

private void placeGhostsRandomly(Player player, int rowStart, int rowEnd, int good, int bad) {
    while (good > 0 || bad > 0) {
        int row = rowStart + (int) (Math.random() * (rowEnd - rowStart + 1));
        int col = (int) (Math.random() * 6);
        if (board[row][col].equals(" ")) {
            if (good > 0) {
                board[row][col] = player.getUsername() + "_w";
                good--;
            } else {
                board[row][col] = player.getUsername() + "_v";
                bad--;
            }
        }
    }
}

private void placeGhostsManually(Player player, int rowStart, int rowEnd, int good, int bad) {
    System.out.println("Colocación manual para " + player.getUsername());
    while (good > 0 || bad > 0) {
        printBoard();
        System.out.println("Fantasmas restantes - Buenos: " + good + ", Malos: " + bad);
        System.out.print("Ingrese fila y columna (separados por un espacio): ");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        scanner.nextLine(); 

        if (row < rowStart || row > rowEnd || col < 0 || col >= 6 || !board[row][col].equals(" ")) {
            System.out.println("Posición inválida. Intente de nuevo.");
            continue;
        }

        if (good > 0) {
            board[row][col] = player.getUsername() + "_w";
            good--;
        } else {
            board[row][col] = player.getUsername() + "_v";
            bad--;
        }
    }
}


private boolean isGameOver(Player player1, Player player2) {
    // aca   verificar si alguien capturó todos los fantasmas buenos
    if (countGhosts(player1, "_w") == 0) {
        System.out.println(player2.getUsername() + " triunfó porque capturó todos los fantasmas buenos de " + player1.getUsername() + "!");
        player2.addPoints(3);
        addGameLog(player2, player1, "capturó todos los fantasmas buenos");
        return true;
    }
    if (countGhosts(player2, "_w") == 0) {
        System.out.println(player1.getUsername() + " triunfó porque capturó todos los fantasmas buenos de " + player2.getUsername() + "!");
        player1.addPoints(3);
        addGameLog(player1, player2, "capturó todos los fantasmas buenos");
        return true;
    }

    // Verificar si alguien perdió todos sus fantasmas malos
    if (countGhosts(player1, "_v") == 0) {
        System.out.println(player2.getUsername() + " triunfó porque " + player1.getUsername() + " perdió todos sus fantasmas malos!");
        player2.addPoints(3);
        addGameLog(player2, player1, "porque el oponente perdió todos sus fantasmas malos");
        return true;
    }
    if (countGhosts(player2, "_v") == 0) {
        System.out.println(player1.getUsername() + " triunfó porque " + player2.getUsername() + " perdió todos sus fantasmas malos!");
        player1.addPoints(3);
        addGameLog(player1, player2, "porque el oponente perdió todos sus fantasmas malos");
        return true;
    }

    // Verificar si un fantasma bueno alcanzo la salida del castillo enemigo
    if (isGhostAtExit(player1, "_w", 5, 0) || isGhostAtExit(player1, "_w", 5, 5)) {
        System.out.println(player1.getUsername() + " triunfó al sacar un fantasma bueno!");
        player1.addPoints(3);
        addGameLog(player1, player2, "triunfó al sacar un fantasma bueno");
        return true;
    }
    if (isGhostAtExit(player2, "_w", 0, 0) || isGhostAtExit(player2, "_w", 0, 5)) {
        System.out.println(player2.getUsername() + " triunfó al sacar un fantasma bueno!");
        player2.addPoints(3);
        addGameLog(player2, player1, "triunfó al sacar un fantasma bueno");
        return true;
    }

    return false;
}

private int countGhosts(Player player, String type) {
    int count = 0;
    for (int i = 0; i < 6; i++) {
        for (int j = 0; j < 6; j++) {
            if (board[i][j].equals(player.getUsername() + type)) {
                count++;
            }
        }
    }
    return count;
}

private boolean isGhostAtExit(Player player, String type, int row, int col) {
    return board[row][col].equals(player.getUsername() + type);
}

private void addGameLog(Player winner, Player loser, String reason) {
    String log = winner.getUsername() + " triunfó sobre " + loser.getUsername() + " " + reason + "!";
    winner.addLog(log);
    loser.addLog(log);
}

// método principal para poder hacer turnos y detectar fin del juego
private void playGame() {
    if (currentPlayer == null) {
        System.out.println("Debe iniciar sesión para jugar.");
        return;
    }

    System.out.print("Ingrese el nombre del jugador 2: ");
    String opponentName = scanner.nextLine();
    Player opponent = findPlayerByName(opponentName);

    if (opponent == null) {
        System.out.println("El jugador 2 no existe.");
        return;
    }

    initializeGameBoard(currentPlayer, opponent);

    boolean gameOver = false;
    while (!gameOver) {
        playTurn(currentPlayer, opponent);
        gameOver = isGameOver(currentPlayer, opponent);
        if (gameOver) break;

        playTurn(opponent, currentPlayer);
        gameOver = isGameOver(opponent, currentPlayer);
    }

    System.out.println("El juego ha terminado. Regresando al Menú Principal...");
}

private Player findPlayerByName(String username) {
    for (Player player : players) {
        if (player != null && player.getUsername().equals(username)) {
            return player;
        }
    }
    return null;
}

///////////////////////////\\
private void generateReports() {
    int option;
    do {
        System.out.println("Reportes");
        System.out.println("1- Descripción de mis últimos 10 juegos");
        System.out.println("2- Ranking de Jugadores");
        System.out.println("3- Regresar al Menú Principal");
        System.out.print("Seleccione una opción: ");
        option = scanner.nextInt();
        scanner.nextLine(); 

        switch (option) {
            case 1 -> showLast10Games();
            case 2 -> showPlayerRanking();
            case 3 -> System.out.println("Regresando al Menú Principal...");
            default -> System.out.println("Opción no válida.");
        }
    } while (option != 3);
}

private void showLast10Games() {
    if (currentPlayer == null) {
        System.out.println("Debe iniciar sesión para ver sus reportes.");
        return;
    }

    System.out.println("Últimos 10 juegos de " + currentPlayer.getUsername() + ":");
    String[] logs = currentPlayer.getLogs();
    boolean hasLogs = false;

    for (int i = logs.length - 1; i >= 0; i--) {
        if (logs[i] != null) {
            System.out.println("- " + logs[i]);
            hasLogs = true;
        }
    }

    if (!hasLogs) {
        System.out.println("No hay juegos registrados.");
    }
}

private void showPlayerRanking() {
    System.out.println("Ranking de Jugadores:");
    Player[] sortedPlayers = getSortedPlayers();

    if (sortedPlayers.length == 0) {
        System.out.println("No hay jugadores registrados.");
        return;
    }

    for (Player player : sortedPlayers) {
        if (player != null) {
            System.out.println("Usuario: " + player.getUsername() + ", Puntos: " + player.getPoints());
        }
    }
}

private Player[] getSortedPlayers() {
    // clone el arreglo para modificarlo sin afectar el original
    Player[] clonedPlayers = new Player[players.length];
    System.arraycopy(players, 0, clonedPlayers, 0, players.length);

    // estoy usando un algoritmo simple
    for (int i = 0; i < clonedPlayers.length; i++) {
        for (int j = 0; j < clonedPlayers.length - i - 1; j++) {
            if (clonedPlayers[j] != null && clonedPlayers[j + 1] != null) {
                if (clonedPlayers[j].getPoints() < clonedPlayers[j + 1].getPoints()) {
                    Player temp = clonedPlayers[j];
                    clonedPlayers[j] = clonedPlayers[j + 1];
                    clonedPlayers[j + 1] = temp;
                }
            }
        }
    }

    return clonedPlayers;
}

private void manageProfile() {
    int option;
    do {
        System.out.println("Mi Perfil");
        System.out.println("1- Ver mis datos");
        System.out.println("2- Cambiar contraseña");
        System.out.println("3- Eliminar cuenta");
        System.out.println("4- Regresar al Menú Principal");
        System.out.print("Seleccione una opción: ");
        option = scanner.nextInt();
        scanner.nextLine(); 

        switch (option) {
            case 1 -> viewProfile();
            case 2 -> changePassword();
            case 3 -> deleteAccount();
            case 4 -> System.out.println("Regresando al Menú Principal...");
            default -> System.out.println("Opción no válida.");
        }
    } while (option != 4);
}

private void viewProfile() {
    if (currentPlayer == null) {
        System.out.println("Debe iniciar sesión para ver sus datos.");
        return;
    }
    System.out.println("Datos del jugador:");
    System.out.println("Usuario: " + currentPlayer.getUsername());
    System.out.println("Puntos: " + currentPlayer.getPoints());
}

private void changePassword() {
    if (currentPlayer == null) {
        System.out.println("Debe iniciar sesión para cambiar su contraseña.");
        return;
    }
    System.out.print("Ingrese la nueva contraseña (mínimo 8 caracteres): ");
    String newPassword = scanner.nextLine();
    if (newPassword.length() >= 8) {
        currentPlayer.changePassword(newPassword);
        System.out.println("Contraseña actualizada con éxito.");
    } else {
        System.out.println("Contraseña demasiado corta. Intente nuevamente.");
    }
}

private void deleteAccount() {
    if (currentPlayer == null) {
        System.out.println("Debe iniciar sesión para eliminar su cuenta.");
        return;
    }
    System.out.print("¿Está seguro de que desea eliminar su cuenta? (S/N): ");
    String confirm = scanner.nextLine();
    if (confirm.equalsIgnoreCase("S")) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null && players[i].equals(currentPlayer)) {
                players[i] = null;
                currentPlayer = null;
                System.out.println("Cuenta eliminada con éxito. Regresando al menú de inicio...");
                return;
            }
        }
    } else {
        System.out.println("Cancelado.");
    }
}

    private void playTurn(Player currentPlayer, Player opponent) {
    boolean turnValid = false;
    while (!turnValid) {
        printBoard();
        System.out.println("Turno de " + currentPlayer.getUsername());
        System.out.print("Seleccione el fantasma (fila y columna, o -1 -1 para retirarse): ");
        int srcRow = scanner.nextInt();
        int srcCol = scanner.nextInt();
        scanner.nextLine(); 

        if (srcRow == -1 && srcCol == -1) {
            System.out.print("¿Está seguro de que desea retirarse? (S/N): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("S")) {
                System.out.println(currentPlayer.getUsername() + " se retiró. " + opponent.getUsername() + " gana por retiro.");
                opponent.addPoints(3);
                addGameLog(opponent, currentPlayer, "triunfó por retiro del oponente");
                return;
            } else {
                System.out.println("Retiro cancelado. Continúe jugando.");
                continue;
            }
        }

        if (!isValidSelection(currentPlayer, srcRow, srcCol)) {
            System.out.println("Selección inválida. Intente de nuevo.");
            continue;
        }

        System.out.print("Seleccione la posición de movimiento (fila y columna): ");
        int destRow = scanner.nextInt();
        int destCol = scanner.nextInt();
        scanner.nextLine(); 

        if (isValidMove(srcRow, srcCol, destRow, destCol)) {
            if (!board[destRow][destCol].equals(" ")) {
                System.out.println("¡Te has comido un fantasma " + (board[destRow][destCol].contains("_w") ? "bueno" : "malo") + " de " + opponent.getUsername() + "!");
            }
            board[destRow][destCol] = board[srcRow][srcCol];
            board[srcRow][srcCol] = " ";
            turnValid = true;
        } else {
            System.out.println("Movimiento inválido. Intente de nuevo.");
        }
    }
}

}

