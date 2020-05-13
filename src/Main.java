import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

enum InstructionTypes {
    START,
    LOAD,
    LOADM,
    STORE,
    CMPM,
    JMP,
    CJMP,
    ADD,
    ADDM,
    SUB,
    SUBM,
    MUL,
    MULM,
    DISP,
    HALT,
}



public class Main {
    public static int[] M = new int[256];
    public static int F = 0;
    public static boolean isStarted = false;
    public static HashMap<Integer,String> fullData = new HashMap<Integer, String>();    //Integer of HashMap equals to PC
    public static int AC = 0;
    public static int PC = 0;
    public static int address = 0;
    public static String instruction = "";
    public static InstructionTypes type;

    public static void main(String[] args) throws IOException, InterruptedException {
        //Command line code to run
        //java Main args[0]
        readAndAddData(args[0]);

        //Fill Memory with 0's
        for (int val:M) {
            val = 0;
        }

        if(fullData.get(0).equals("START"))
        {
            isStarted = true;
            while (isStarted) {
                //Thread.sleep(100); //To see how program compute and display output by order

                String[] instructionList = fullData.get(PC).split(" "); //EX:  {LOAD, 20}
                instruction = instructionList[0];                             // {LOAD}
                if (instructionList.length > 1) {
                    address = Integer.parseInt(instructionList[1]);
                }                         // {20}

                //Convert STRING -> ENUM
                type = InstructionTypes.valueOf(instruction);

                switch (type)
                {
                    case START:START();break;
                    case LOAD:LOAD(address);break;
                    case LOADM:LOADM(address);break;
                    case STORE:STORE(address);break;
                    case CMPM:CMPM(address);break;
                    case JMP:JMP(address);break;
                    case CJMP:CJMP(address);break;
                    case ADD:ADD(address);break;
                    case ADDM:ADDM(address);break;
                    case SUB:SUB(address);break;
                    case SUBM:SUBM(address);break;
                    case MUL:MUL(address);break;
                    case MULM:MULM(address);break;
                    case DISP:DISP();break;
                    case HALT:HALT();break;
                }
            }

        }else {
            System.out.println("There is no START instruction in code lines");
            return;
        }
    }

    //Read text file and add to fullData HashMap
    public static void readAndAddData(String inputText) throws IOException {
        Scanner file = null;
        try {
            file = new Scanner(new File(inputText));
        } catch (Exception ex) {
            System.out.println("Can not open file.");
            System.exit(0);
        }
        while (file.hasNext()) {
            String line = file.nextLine();
            if (line == null) {
                break;
            }
            String[] parts = line.split(" ",2);
            for(int i = 0; i < parts.length;i++)
            {
                fullData.put(Integer.parseInt(parts[0]),parts[1]);
            }
        }

        file.close();
    }

    //region INSTRUCTIONS
    ////////////////////////////////////////////
    //Instruction to start the execution
    public static void START(){
        System.out.println("Program execution started!");
        isStarted = true;
        PC++;
    }
    //Instruction to load x to AC
    public static void LOAD(int x) {
        AC=x;
        PC++;
//        displayValues(x);
    }
    //Instruction to load M[x] to AC
    public static void LOADM(int x) {
        AC = M[x];
        PC++;
//        displayValues(x);
    }
    //Instruction to store AC to M[x]
    public static void STORE(int x) {
        M[x] = AC;
        PC++;
//        displayValues(x);
    }
    //Instruction to change F value according to relationship between AC and M[x]
    public static void CMPM(int x) {
        if(AC > M[x]) F = 1;
        if(AC < M[x]) F = -1;
        if(AC == M[x]) F = 0;

        PC++;
//        displayValues(x);

    }
    //Instruction to change PC value according to F value
    public static int CJMP(int x) {
//        displayValues(x);
        return F >= 0 ? PC = x : PC++;
    }
    //Instruction to change PC value to x
    public static void JMP(int x) {
        PC = x;
//        System.out.println("JUMPED BACK TO "+ PC);
//       displayValues(x);
    }
    //Instruction to subtract x from AC
    public static void ADD(int x) {
        AC +=x;
        PC++;
//        displayValues(x);
    }
    //Instruction to add M[x] to AC
    public static void ADDM(int x) {
        AC += M[x];
        PC++;
//        displayValues(x);
    }
    //Instruction to subtract x from AC
    public static void SUB(int x) {
        AC -=x;
        PC++;
//        displayValues(x);
    }
    //Instruction to subtract M[x] from AC
    public static void SUBM(int x) {
        AC -= M[x];
        PC++;
//        displayValues(x);
    }
    //Instruction to multiple AC with x
    public static void MUL(int x) {
        AC *= x;
        PC++;
//        displayValues(x);
    }
    //Instruction to multiple AC with M[x]
    public static void MULM(int x) {
        AC *= M[x];
        PC++;
//        displayValues(x);
    }
    //Instruction to show AC value
    public static void DISP() {
        //System.out.println("AC: "+AC);
        System.out.print(AC);
        PC++;
    }
    //Instruction to stop the execution
    public static void HALT() {
        System.out.println("\nProgram execution stopped!");
        isStarted = false;
    }
    //Instruction to show statistics on screen after proper instruction --> AC, PC, M[x], F values
    public static void displayValues(int x){
        System.out.println("---After " + type.toString() + ' ' + x + " Instruction---\n" + "AC: " + AC + " PC:" + PC + " M: " + M[x] + " F: " + F);
    }
    ////////////////////////////////////////////
    //end region


}
