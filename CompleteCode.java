import java.io.*;
import java.util.*;
public class CompleteCode {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner main = new Scanner(System.in);
        //files path with information or just string with information
        String majorFile = "C:\\Users\\naxos\\OneDrive\\Desktop\\Majors.txt";//add majorfile;
                String jobsFile = "C:\\Users\\naxos\\OneDrive\\Desktop\\Jobs.txt"; //add jobFile;
                String salariesFile = "C:\\Users\\naxos\\OneDrive\\Desktop\\JobsSalaries.txt"; //add salariesFile;

                //interactive portion
                String major = majorChoice(main, majorFile);
        String job = jobChoice(main, jobsFile, major);
        int salary = getSalary(salariesFile, job);
        String loanSummary = loanCalculuation(main, major, job, salary);

        //will print out all information on a final file (need to find out how to make a file)
        String finalFile = fileCreation(main);
        if(finalFile.equals("This is the end of the program.")) {
            PrintStream finalOutput = new PrintStream(new File(finalFile));
            finalOutput.print(loanSummary);
        }
    }

    // majors
    public static String majorChoice(Scanner main,String majorFile) throws FileNotFoundException {
        Scanner majors = new Scanner(new File(majorFile));
        System.out.println("This is an interactive program that prompts the user to choose a major from a four-year university,\nthen choose a corresponding job title. The user will input information about their student\nloan to determine if their average salary will be able to cover their monthly loan payments.\nThis is applicable to universities in the United States only.\n");
        System.out.println("Please choose a major from the following list:");
        String[] majorsChoices = new String[50]; //dependent on #majors;
        for (int j = 0; j < majorsChoices.length; j++) {
            majorsChoices[j] = majors.nextLine();
            System.out.println(majorsChoices[j]);
        }
        System.out.print("\nChosen major: ");
        String major = main.nextLine();
        int i = 0;
        while (i < majorsChoices.length) {
            if (major.equalsIgnoreCase(majorsChoices[i])) {
                major = majorsChoices[i];
                i = majorsChoices.length;
            }
            i++;
            if (i == majorsChoices.length) {
                i = 0;
                System.out.println("Invalid major inputted. Please try again and choose from the list provided.");
                System.out.print("Chosen major: ");
                major = main.nextLine();
            }
        }
        return major;
    }

    //jobs
    public static String jobChoice(Scanner main, String jobsFile, String major) throws FileNotFoundException {
        Scanner jobFile = new Scanner(new File(jobsFile));
        String jobs[] = new String[10]; //depends on #jobs following majors
        String majorInFile = "Jobs you can get with a four-year " + major + " degree:";
        //prints jobs of major
        System.out.println("\n" + majorInFile);
        while (jobFile.hasNextLine()) {
            if (jobFile.nextLine().equalsIgnoreCase(majorInFile)) {
                for (int i = 0; i < jobs.length; i++) {
                    jobs[i] = jobFile.nextLine();
                    System.out.println(jobs[i]);
                }
            }
        }
        //interacts with user to ask for them to pick a job
        System.out.print("\nWhich job interests you? ");
        String job = main.nextLine();
        int salary = 0;
        int i = 0;
        while (i < jobs.length) {
            if (job.equalsIgnoreCase(jobs[i])) {
                job = jobs[i];
                salary =
                        i = jobs.length;
            }
            i++;
            if (i == jobs.length) {
                i = 0;
                System.out.println("Invalid job inputted. Please choose a job from the list above.");
                System.out.print("Job of Interest: ");
                job = main.nextLine();
            }
        }
        return job;
    }

    //salary
    public static int getSalary(String salariesFile, String job) throws FileNotFoundException {
        Scanner salaries = new Scanner(new File(salariesFile)); //
        while(salaries. hasNextLine()) {
            if(job.equalsIgnoreCase(salaries.nextLine())) {
                return salaries.nextInt();
            }
        }
        return 0;
    }

    // loan information
    public static String loanCalculuation(Scanner main, String major, String job, int salary) throws FileNotFoundException {
        System.out.println("\nBased on your career of interest the following prompts will provide some information on your student loan payments.");
        System.out.printf("The national average yearly salary for a %s is $%d.\n", job, salary);
        //loan
        System.out.print("\nStudent loan amount(no more than six digits): $");
        while (!main.hasNextInt()) {
            System.out.println("Invalid loan amount inputted. Please try again.");
            System.out.print("Student loan amount(no more than six digits): $");
            main.nextLine();
        }
        int loanAmount = main.nextInt();

        //loan rate
        System.out.print("\nInterest rate on loan (as a percentage): ");
        while (!main.hasNextDouble()) {
            main.next();
            System.out.println("Invalid interest rate inputted. Please make sure your loan rate is a percent (without the percent symbol).");
            System.out.print("Interest rate on loan (as a percentage): ");
            main.nextLine();
        }
        double loanRate = main.nextDouble();

        //term of loan
        System.out.print("\nTerm of loan (in years): ");
        while (!main.hasNextInt()) {
            main.next();
            System.out.println("Invalid loan term inputted. Make sure you are inputting a numerical value, no special characters.");
            System.out.print("Term of loan (in years): ");
            main.nextLine();
        }
        int loanTerm = main.nextInt();

        // calculation monthly loan payment
        double loanMonthlyPay = loanRate / 12.0/100 * (loanAmount / (1 - (1 / Math.pow(1 + loanRate / 12.0/100, loanTerm * 12))));
        double loanPercentSalary = loanMonthlyPay *12/ salary * 100;
        String loanSummary = "Major: " + major + "\nChosen job: " + job + "\nAverage yearly salary: " + salary
                + "\nLoan: " + loanAmount + "\nInterest rate: " + roundingTenths(loanRate) + "\nTerm of loan: " + loanTerm
                + " years" + "\nMonthly loan payment: $" + roundingTenths(loanMonthlyPay) + "\nPercent of monthly salary going towards loan: "
                + roundingTenths(loanPercentSalary);
        System.out.println("\nSummary of provided information: \n" + loanSummary);
        return loanSummary;
    }
    public static double roundingTenths(double numberToRound) {
        return (int) Math.round(numberToRound*100)/100.0;
    }

    //final file output
    public static String fileCreation(Scanner main) throws FileNotFoundException {
        System.out.print("Do you want a copy of this information stored on a separate file? ");
        String response = main.next();
        while (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no")) {
            System.out.println("Invalid response (yes/no) ");
            System.out.print("Do you want a copy of this information stored on a separate file? ");
            response = main.next();
        }
        if (response.equalsIgnoreCase("yes")) {
            System.out.print("Type in a file name: "); // user error for file names
            String outputFileName = main.next() + ".txt";
            while ((outputFileName.indexOf('@') != -1) || (outputFileName.indexOf('$') != -1) || (outputFileName.indexOf('%') != -1) || (outputFileName.indexOf('&') != -1) ||
                    (outputFileName.indexOf('\\') != -1) || (outputFileName.indexOf('/') != -1) || (outputFileName.indexOf(':') != -1) ||
                    (outputFileName.indexOf('*') != -1) || (outputFileName.indexOf('?') != -1) || (outputFileName.indexOf('\"') != -1) ||
                    (outputFileName.indexOf('\'') != -1) || (outputFileName.indexOf('<') != -1) || (outputFileName.indexOf('>') != -1) ||
                    (outputFileName.indexOf('|') != -1) || (outputFileName.indexOf('~') != -1) || (outputFileName.indexOf('`') != -1) ||
                    (outputFileName.indexOf('#') != -1) || (outputFileName.indexOf('^') != -1) || (outputFileName.indexOf('+') != -1) ||
                    (outputFileName.indexOf('=') != -1) || (outputFileName.indexOf('{') != -1) || (outputFileName.indexOf('}') != -1) ||
                    (outputFileName.indexOf('[') != -1) || (outputFileName.indexOf(']') != -1) || (outputFileName.indexOf(';') != -1) || (outputFileName.indexOf('!') != -1)) { // have to account for special characters;
                System.out.println("Invalid file name.");
                System.out.print("Do you want information on proper file names? yes/no ");
                String answerFileInfo = main.next();
                if (answerFileInfo.equalsIgnoreCase("yes")) {
                    System.out.println("\nAllowed: \nletters, number, (), _, -, , ,, .");
                    System.out.println("\nNot Allowed: \n*spaces\n*Special Characters: @, $, %, &, \\, /, :, *, ?, \"," +
                            " ', <, >, |, ~, `, #, ^, +, =, {, }, [, ], ;, !\n");
                }
                System.out.print("Type in a file name: ");
                outputFileName = main.next();
            }
            return outputFileName;
        }
        return "This is the end of the program.";
    }
}