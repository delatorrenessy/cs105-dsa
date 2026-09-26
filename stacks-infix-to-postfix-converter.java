public class SimpleConverter {
    public static void main(String[] args) {
        // The math question we want to change
        String infix = "a+b*c";
        
        // 1. Setup the "Waiting Room" (Our Stack)
        char[] waitingRoom = new char[infix.length()];
        int waitingRoomPointer = -1; // -1 means the room is empty

        // 2. Setup the "Final Answer Box"
        char[] finalAnswer = new char[infix.length()];
        int answerCount = 0;

        // 3. Look at every single letter/symbol one by one
        for (int i = 0; i < infix.length(); i++) {
            char currentSymbol = infix.charAt(i);

            // IS IT A LETTER? (a, b, c, etc.)
            // If it is a normal letter, send it straight to the final answer!
            if (Character.isLetter(currentSymbol)) {
                finalAnswer[answerCount] = currentSymbol;
                answerCount = answerCount + 1;
            }
            
            // IS IT AN OPENING WALL? '('
            // Put it into the waiting room.
            else if (currentSymbol == '(') {
                waitingRoomPointer = waitingRoomPointer + 1;
                waitingRoom[waitingRoomPointer] = currentSymbol;
            }
            
            // IS IT A CLOSING WALL? ')'
            // Take everything out of the waiting room until we see the '('
            else if (currentSymbol == ')') {
                while (waitingRoomPointer >= 0 && waitingRoom[waitingRoomPointer] != '(') {
                    // Move operator to the final answer
                    finalAnswer[answerCount] = waitingRoom[waitingRoomPointer];
                    answerCount = answerCount + 1;
                    waitingRoomPointer = waitingRoomPointer - 1; // remove it
                }
                // Throw away the '(' symbol from the waiting room
                waitingRoomPointer = waitingRoomPointer - 1;
            }
            
            // IT MUST BE A MATH OPERATOR! (+, -, *, /)
            else {
                // Find out how strong the current symbol is
                int currentStrength = 0;
                if (currentSymbol == '+' || currentSymbol == '-') { currentStrength = 1; }
                if (currentSymbol == '*' || currentSymbol == '/') { currentStrength = 2; }

                // Check if the symbol already sitting in the waiting room is stronger or equal
                while (waitingRoomPointer >= 0) {
                    char topSymbol = waitingRoom[waitingRoomPointer];
                    
                    int topStrength = 0;
                    if (topSymbol == '+' || topSymbol == '-') { topStrength = 1; }
                    if (topSymbol == '*' || topSymbol == '/') { topStrength = 2; }
                    
                    // If the symbol inside is stronger or equal, kick it out to the final answer!
                    if (topStrength >= currentStrength) {
                        finalAnswer[answerCount] = topSymbol;
                        answerCount = answerCount + 1;
                        waitingRoomPointer = waitingRoomPointer - 1; // remove it
                    } else {
                        // The symbol inside is weaker, so stop kicking things out!
                        break;
                    }
                }

                // Now, put our current symbol into the waiting room safely
                waitingRoomPointer = waitingRoomPointer + 1;
                waitingRoom[waitingRoomPointer] = currentSymbol;
            }
        }

        // 4. Everything is scanned! Empty out whatever is left in the waiting room
        while (waitingRoomPointer >= 0) {
            finalAnswer[answerCount] = waitingRoom[waitingRoomPointer];
            answerCount = answerCount + 1;
            waitingRoomPointer = waitingRoomPointer - 1;
        }

        // 5. Convert our answer box into a regular word and print it!
        String result = new String(finalAnswer, 0, answerCount);
        System.out.println("The postfix answer is: " + result);
    }
}
