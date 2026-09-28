public class SimpleMathEngine {
    public static void main(String[] args) {
        // The math question we want to solve
        String infix = "2+3*4";
        System.out.println("1. Starting Math Question: " + infix);

        // ====================================================
        // PHASE 1: CONVERT INFIX TO POSTFIX (The Waiting Room)
        // ====================================================
        
        char[] waitingRoom = new char[infix.length()];
        int waitingRoomPointer = -1; 

        char[] postfixContainer = new char[infix.length()];
        int postfixCount = 0;

        for (int i = 0; i < infix.length(); i++) {
            char currentSymbol = infix.charAt(i);

            // IS IT A NUMBER? (0 to 9)
            if (Character.isDigit(currentSymbol)) {
                postfixContainer[postfixCount] = currentSymbol;
                postfixCount = postfixCount + 1;
            }
            // IS IT A WALL? '('
            else if (currentSymbol == '(') {
                waitingRoomPointer = waitingRoomPointer + 1;
                waitingRoom[waitingRoomPointer] = currentSymbol;
            }
            // IS IT A CLOSING WALL? ')'
            else if (currentSymbol == ')') {
                while (waitingRoomPointer >= 0 && waitingRoom[waitingRoomPointer] != '(') {
                    postfixContainer[postfixCount] = waitingRoom[waitingRoomPointer];
                    postfixCount = postfixCount + 1;
                    waitingRoomPointer = waitingRoomPointer - 1; 
                }
                waitingRoomPointer = waitingRoomPointer - 1; // Throw away '('
            }
            // IT IS A MATH OPERATOR! (+, -, *, /)
            else {
                int currentStrength = 0;
                if (currentSymbol == '+' || currentSymbol == '-') { currentStrength = 1; }
                if (currentSymbol == '*' || currentSymbol == '/') { currentStrength = 2; }

                while (waitingRoomPointer >= 0) {
                    char topSymbol = waitingRoom[waitingRoomPointer];
                    
                    int topStrength = 0;
                    if (topSymbol == '+' || topSymbol == '-') { topStrength = 1; }
                    if (topSymbol == '*' || topSymbol == '/') { topStrength = 2; }
                    
                    if (topStrength >= currentStrength) {
                        postfixContainer[postfixCount] = topSymbol;
                        postfixCount = postfixCount + 1;
                        waitingRoomPointer = waitingRoomPointer - 1; 
                    } else {
                        break;
                    }
                }
                waitingRoomPointer = waitingRoomPointer + 1;
                waitingRoom[waitingRoomPointer] = currentSymbol;
            }
        }

        // Empty out remaining operators
        while (waitingRoomPointer >= 0) {
            postfixContainer[postfixCount] = waitingRoom[waitingRoomPointer];
            postfixCount = postfixCount + 1;
            waitingRoomPointer = waitingRoomPointer - 1;
        }

        // Turn our postfix container array into a readable String
        String postfixResult = new String(postfixContainer, 0, postfixCount);
        System.out.println("2. Changed to Postfix Form: " + postfixResult);


        // ====================================================
        // PHASE 2: EVALUATE THE POSTFIX (The Calculation Stack)
        // ====================================================
        
        // This stack stores raw whole NUMBERS, not symbols!
        int[] calculationStack = new int[postfixResult.length()];
        int calcPointer = -1; // Empty stack

        for (int i = 0; i < postfixResult.length(); i++) {
            char currentSymbol = postfixResult.charAt(i);

            // IF IT IS A NUMBER: Drop it onto the calculation stack
            if (Character.isDigit(currentSymbol)) {
                calcPointer = calcPointer + 1;
                // '0' in character math matches code 48. Subtracting '0' gives us the real int.
                calculationStack[calcPointer] = currentSymbol - '0'; 
            } 
            // IF IT IS AN OPERATOR: Pull out the top two numbers and do math!
            else {
                // Take out the top number (Second one added)
                int numberB = calculationStack[calcPointer];
                calcPointer = calcPointer - 1;

                // Take out the next number down (First one added)
                int numberA = calculationStack[calcPointer];
                calcPointer = calcPointer - 1;

                int mathAnswer = 0;
                if (currentSymbol == '+') { mathAnswer = numberA + numberB; }
                if (currentSymbol == '-') { mathAnswer = numberA - numberB; }
                if (currentSymbol == '*') { mathAnswer = numberA * numberB; }
                if (currentSymbol == '/') { mathAnswer = numberA / numberB; }

                // Put the math answer right back into the calculation stack
                calcPointer = calcPointer + 1;
                calculationStack[calcPointer] = mathAnswer;
            }
        }

        // The very last number remaining in the stack is our grand final answer!
        int finalGrandAnswer = calculationStack[calcPointer];
        System.out.println("3. Grand Final Math Answer: " + finalGrandAnswer);
    }
}
