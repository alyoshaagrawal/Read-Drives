import FileInfo.FileInfo;
import java.util.*;
import java.time.*;
import java.text.*;
import java.io.*;
import org.apache.commons.io.*;
import org.apache.commons.io.comparator.SizeFileComparator;

public class DrivesListingExample {
	static String username;
	static long Chgtime;
	static Date SRdate;
	static Date ERdate;
	static long lastMod;
	static File FileLM;

	static void RecursivePrint(File[] arr, int index, int level, String choice) {
		// terminate condition
		if (index == arr.length)
			return;

		// tabs for internal levels
		/*
		 * for (int i = 0; i < level; i++) System.out.print("\t");
		 */

		// for files
		if (arr[index].isFile())
			try {
				FileInfo file = new FileInfo(arr[index]);

				if (choice.equals("a")) {
					if (file.getOwner().equals(username) && (arr[index].lastModified() > Chgtime)) {
						System.out.println(arr[index].lastModified() + "   " + Chgtime);
						System.out.println("Name: " + file.getName());
						System.out.println("Absolute path: " + file.getAbsolutePath());
						System.out.println("Size: " + file.getSize());
						System.out.println("Type: " + file.filetype());
						System.out.println("Last modified: " + file.getLastModified());
						System.out.println("Owner: " + file.getOwner());
						System.out.println("Created: " + file.getCreated() + "\n\n");
					}
				} else if (choice.equals("b")) {
					if ((arr[index].lastModified() > SRdate.getTime())
							&& (arr[index].lastModified() < ERdate.getTime())) {

						System.out.println("Name: " + file.getName());
						System.out.println("Absolute path: " + file.getAbsolutePath());
						System.out.println("Size: " + file.getSize());
						System.out.println("Type: " + file.filetype());
						System.out.println("Last modified: " + file.getLastModified());
						System.out.println("Owner: " + file.getOwner());
						System.out.println("Created: " + file.getCreated() + "\n\n");
					}
				} else if (choice.equals("c")) {
					try {
						String ext = FilenameUtils.getExtension(arr[index].getName());
						if (ext.matches("txt|log")) {
							// System.out.println(ext);

							if (arr[index].lastModified() > lastMod) {
								FileLM = arr[index];
								lastMod = arr[index].lastModified();
							}

						}
					} catch (Exception e) {
						System.out.println("Unable to check extension");
					}
				}
			} catch (Exception e) {
				System.out.println("Unable to check file");
			}
		// for sub-directories
		else if (arr[index].isDirectory()) {
			// System.out.println("[" + arr[index].getName() + "]");
			try {
				// recursion for sub-directories
				RecursivePrint(arr[index].listFiles(), 0, level + 1, choice);
			} catch (Exception e) {
				// System.out.println("Unable to check directory");
			}
		}

		// recursion for main directory
		RecursivePrint(arr, ++index, level, choice);

	}

	// Driver Method
	public static void main(String[] args) {
		String choice;

		Scanner in = new Scanner(System.in);
		System.out.println("Enter choice a,b,c");
		choice = in.nextLine();
		if (choice.equals("a")) {
			Scanner in1 = new Scanner(System.in);
			System.out.println("Enter username");
			username = in1.nextLine();
			System.out.println("You entered " + username);
			LocalDateTime chgdate = chgdate(LocalDateTime.now(), 150);
			System.out.println(chgdate);
			Chgtime = chgdate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			System.out.println(Chgtime);
			File[] drives = File.listRoots();
			if (drives != null && drives.length > 0) {
				for (File aDrive : drives) {
					// Provide full path for directory(change accordingly)
					// String maindirpath = aDrive.toString() + "\\";
					String maindirpath = "D:\\";
					// File object
					File maindir = new File(maindirpath);

					// array for files and sub-directories
					// of directory pointed by maindir
					File arr[] = maindir.listFiles();

					System.out.println("**********************************************");
					System.out.println("Files from main directory : " + maindir);
					System.out.println("**********************************************");

					// Calling recursive method
					RecursivePrint(arr, 0, 0, choice);
				}
			}
		} else if (choice.equals("b")) {
			Scanner sc = new Scanner(System.in);
			while (true) {
				System.out.println("Enter Range Start Date: mm/dd/yyyy");
				String StrDt = sc.nextLine();
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
				df.setLenient(false);
				try {
					SRdate = df.parse(StrDt);
					System.out.println(StrDt + " is a valid Date");
					break;
				} catch (ParseException e) {
					System.out.println("Unable to parse " + StrDt);
				}
				System.out.println(StrDt + " is not a valid Date");
				continue;
			}
			while (true) {
				System.out.println("Enter Range end Date: mm/dd/yyyy");
				String StrDt = sc.nextLine();
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
				df.setLenient(false);
				try {
					ERdate = df.parse(StrDt);
					System.out.println(StrDt + " is a valid Date");
					break;
				} catch (ParseException e) {
					System.out.println("Unable to parse " + StrDt);
				}
				System.out.println(StrDt + " is not a valid Date");
				continue;
			}
			File[] drives = File.listRoots();
			if (drives != null && drives.length > 0) {
				for (File aDrive : drives) {
					// Provide full path for directory(change accordingly)
					// String maindirpath = aDrive.toString() + "\\";
					String maindirpath = "D:\\";
					// File object
					File maindir = new File(maindirpath);

					// array for files and sub-directories
					// of directory pointed by maindir
					File arr[] = maindir.listFiles();
					Arrays.sort(arr, SizeFileComparator.SIZE_REVERSE);
					System.out.println("**********************************************");
					System.out.println("Files from main directory : " + maindir);
					System.out.println("**********************************************");

					// Calling recursive method
					RecursivePrint(arr, 0, 0, choice);
				}
			}
		} else if (choice.equals("c")) {
			File[] drives = File.listRoots();
			if (drives != null && drives.length > 0) {
				for (File aDrive : drives) {
					// Provide full path for directory(change accordingly)
					// String maindirpath = aDrive.toString() + "\\";
					String maindirpath = "D:\\";
					// File object
					File maindir = new File(maindirpath);

					// array for files and sub-directories
					// of directory pointed by maindir
					File arr[] = maindir.listFiles();

					System.out.println("**********************************************");
					System.out.println("Files from main directory : " + maindir);
					System.out.println("**********************************************");

					// Calling recursive method
					RecursivePrint(arr, 0, 0, choice);
					FileInfo file1 = new FileInfo(FileLM);
					System.out.println("Name: " + file1.getName());
					System.out.println("Absolute path: " + file1.getAbsolutePath());
					System.out.println("Last modified: " + file1.getLastModified());
					String longest_word = "";
					String current;
					try {
						Scanner sc = new Scanner(new FileReader(FileLM));
						while (sc.hasNext()) {
							current = sc.next();
							if (current.length() > longest_word.length()) {
								longest_word = current;
							}
						}
					} catch (Exception e) {
						System.out.println("Unable to parse " + FileLM.getName());
					}

					System.out.println("Longest word:" + longest_word + "\n\n");

				}
			}
		}
	}

	public static LocalDateTime chgdate(LocalDateTime date, int workdays) {

		if (workdays < 1) {
			return date;
		}

		LocalDateTime result = date;
		int subDays = 0;
		while (subDays < workdays) {
			result = result.minusDays(1);
			if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
				++subDays;
			}
		}

		return result;
	}
}
