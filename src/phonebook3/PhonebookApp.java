package phonebook3;


import java.util.*;
import java.io.*;

class Name implements Comparable<Name>{
	
	private String first,last;
	
	//compareTo method
	public int compareTo(Name n) {
		if(this.last.compareTo(n.last)!=0) {
			if(this.last.compareTo(n.last)>0)
				return 1;
			else
			return -1;
		}
		else {
			if(this.first.compareTo(n.first)>0)
				return 1;
			else
			return -1;
		}
			
	}
	
	public boolean equals(Object o) {
		if(o instanceof Name)
			return this.first.equals(((Name)o).first)&&this.last.equals(((Name)o).last);
		else
			return false;
	}
	
	//constructor
	public Name(String last,String first) {
		this.first=new String(first);
		this.last=new String(last);
	}
	
	//copy constructor
	public Name(Name n) {
		this.first=new String(n.first);
		this.last=new String(n.last);
	}
	
	//copy method
	public Name copy() {
		return new Name(this.first,this.last);
	}
	
	//toString method
	public String toString() {
		return this.first+" "+this.last;
	}
	
	//read method
	public static Name read(Scanner scan) {
		if(scan.hasNext())
			return new Name(scan.next(),scan.next());
		else
			return null;
	}
	
}


//Phone number class
class PhoneNumber{
	
	private String phoneNumber;
	
	//constructor
	public PhoneNumber(String s) {
		this.phoneNumber=new String(s);
	}
	
	//copy constructor
	public PhoneNumber(PhoneNumber phone) {
		this.phoneNumber=new String(phone.phoneNumber);
	}
	
	public PhoneNumber copy() {
		return new PhoneNumber(new String(this.phoneNumber));
	}
	
	public boolean equals(Object o) {
		if(o instanceof PhoneNumber)
			return this.phoneNumber.equals(((PhoneNumber)o).phoneNumber);
		else
			return false;
	}
	
	public String toString() {
		return this.phoneNumber;
	}
	
	public String getNumber() {
		return new String(this.phoneNumber);
	}
	
	//read method
	public static PhoneNumber read(Scanner scan) {
		if(scan.hasNext())
			return new PhoneNumber(scan.next());
		else
			return null;
	}
	
}

class ExtendedPhoneNumber extends PhoneNumber{
	
	private String description;
	
	public ExtendedPhoneNumber(String description,String phoneNumber) {
		super(phoneNumber);
		this.description=new String(description);
	}
	
	public ExtendedPhoneNumber(ExtendedPhoneNumber number) {
		super(number.getNumber());
		this.description=new String(number.description);
	}
	
	public ExtendedPhoneNumber copy() {
		return new ExtendedPhoneNumber(this.description,super.getNumber());
	}
	
	public String toString() {
		return this.description+ ": "+super.getNumber();
	}
	
	//read method
	public static ExtendedPhoneNumber read(Scanner scan) {
		if(scan.hasNext())
			return new ExtendedPhoneNumber(scan.next(),scan.next());
		else
			return null;
	}
	
}

class PhonebookEntry{
	private Name name;
	private ArrayList<ExtendedPhoneNumber> numbers;
	
	public PhonebookEntry(Name name, ArrayList<ExtendedPhoneNumber> numbers) {
		this.name=new Name(name);
		this.numbers= new ArrayList<>(numbers);
	}
	
	public PhonebookEntry(PhonebookEntry phentry) {
		this.name=new Name(phentry.name);
		this.numbers=new ArrayList<>(phentry.numbers);
	}
	
	public Name getName() {
		return new Name(this.name);
	}
	
	public ArrayList<ExtendedPhoneNumber> getPhoneNumbers() {
		return new ArrayList<>(numbers);
	}
	
	public String toString() {
		return this.getName()+": "+this.getPhoneNumbers();
	}
	
	//read method
	public static PhonebookEntry read(Scanner scan) {
		if(scan.hasNext()) {
			Name name=Name.read(scan);
			int n=scan.nextInt();
			ArrayList<ExtendedPhoneNumber> numbers =new ArrayList<>(n);
			for(int i=0;i<n;i++)
				numbers.add(ExtendedPhoneNumber.read(scan));
			return new PhonebookEntry(name,numbers);
		}
		else
			return null;
	}
	
}

class Phonebook{
	private Map<Name,PhonebookEntry> map;
	
	public Phonebook(String in) throws IOException {
		map = new TreeMap<Name,PhonebookEntry>();
		File input=new File(in);
		Scanner scan=new Scanner(input);
		while(scan.hasNextLine()) {
			PhonebookEntry entry=PhonebookEntry.read(scan);
			map.put(entry.getName(),entry);
		}
	}
	
	public PhonebookEntry lookup(Name name) {
		if(map.containsKey(name))
			return new PhonebookEntry(map.get(name));
		else
			return null;
	}
}


public class PhonebookApp {
	public static void main(String[] args) throws IOException {
		
		try {
		boolean notDone=true;
		
		//if(args.length>1)
			//System.exit(1);
		//if(args.length<1)){
			//System.out.println("No command line argument provided");
			//System.exit(1);
		//}
		
		//Phonebook phonebook=new Phonebook(args[0]);
		Phonebook phonebook=new Phonebook("input.txt");
		
		Scanner scan=new Scanner(System.in);
		
		do {
			System.out.print("lookup, quit (l/q)?");
			char c=scan.next().charAt(0);
			
			switch(c) {
				
				case 'l':
					System.out.print(" last name?");
					String last=scan.next();
					System.out.print(" first name?");
					String first=scan.next();
					PhonebookEntry entry=phonebook.lookup(new Name(first,last));
					if(entry!=null)
						System.out.println(entry.getName()+"\'s phone numbers"+entry);
					else
						System.out.println(" -- Name not found\n");
					break;
					
				case 'q':
					notDone=false;
					break;
					
				default:
					System.out.println("User entered invalid input!");
					break;
			}
			
		}while(notDone);
		
		scan.close();
		}
		catch(IOException e) {
			System.out.println("*** IOException ***"+e.getMessage());
		}
		catch(Exception e) {
			System.out.println("*** Exception *** "+e.getMessage());
		}
	}
}
