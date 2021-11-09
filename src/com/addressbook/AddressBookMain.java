package com.addressbook;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AddressBookMain {
    Scanner input = new Scanner(System.in);
    Hashtable<String, ArrayList<contactInfo>> personInfoDict = new Hashtable<>();
    contactInfo p = null;
    ArrayList<contactInfo> pList = new ArrayList<>();

    public Hashtable<String, ArrayList<contactInfo>> insertContactDetails() {
        boolean found = false;
        p = new contactInfo();
        System.out.print("\nEnter the Address Book Name: ");
        String addressBookName = input.next();

        System.out.print("Enter the First Name: ");
        p.setFirst_name(input.next().toString());
        System.out.print("Enter the Last Name: ");
        p.setLast_name(input.next().toString());
        System.out.print("Enter the Address: ");
        p.setAddress(input.next().toString());
        System.out.print("Enter the City: ");
        p.setCity(input.next().toString());
        System.out.print("Enter the State: ");
        p.setState(input.next().toString());
        System.out.print("Enter the Zip: ");
        p.setZip(input.nextInt());
        System.out.print("Enter the Phone Number: ");
        p.setPhone_number(input.next().toString());
        System.out.print("Enter the Email: ");
        p.setEmail(input.next().toString());

        if (personInfoDict.containsKey(addressBookName)) {
            ArrayList<contactInfo> value = personInfoDict.get(addressBookName);
            for (int j = 0; j < value.size(); j++) {

                List<String> names = value.stream().map(contactInfo::getFirst_name).collect(Collectors.toList());
                for (int k = 0; k < names.size(); k++) {
                    if (names.get(j).equals(p.getFirst_name())) {
                        found = true;
                        break;
                    }
                }
            }
            if (found)
                System.out.println("\nDuplicate First Name in Address Book!\n");
            else {
                value.add(p);
                personInfoDict.put(addressBookName, value);
            }
        } else {
            pList = new ArrayList<>();
            pList.add(p);
            personInfoDict.put(addressBookName, pList);
        }

        return personInfoDict;
    }

    public void updateContact(String addressBookName, Hashtable<String, ArrayList<contactInfo>> personInfoDict) {
        boolean flag = findContact(addressBookName, personInfoDict);
        if (flag) {
            editContactDetails(addressBookName, personInfoDict);
        } else {
            System.out.println("\nNo such Address Book found to update!\n");
        }
    }


    public boolean findContact(String addressBookName, Hashtable<String, ArrayList<contactInfo>> personInfoDict) {
        for (int i = 0; i < personInfoDict.size(); i++) {
            if (personInfoDict.containsKey(addressBookName))
                return true;
        }
        return false;
    }


    public void editContactDetails(String addressBookName, Hashtable<String, ArrayList<contactInfo>> personInfoDict) {
        System.out.print("\nEnter the first name you want to edit the details for : ");
        String fName = input.next();

        ArrayList<contactInfo> value = personInfoDict.get(addressBookName);
        for (contactInfo contactInfo : value) {
            if (contactInfo.getFirst_name().equals(fName)) {
                System.out.println("Choose your edit option: ");
                System.out.println("1. Last Name");
                System.out.println("2. Address");
                System.out.println("3. City");
                System.out.println("4. State");
                System.out.println("5. Zip");
                System.out.println("6. Phone Number");
                System.out.println("7. Email");
                int editOption = input.nextInt();

                switch (editOption) {
                    case 1:
                        System.out.println("Enter new Last Name: ");
                        contactInfo.setLast_name(input.next().toString());
                        break;
                    case 2:
                        System.out.println("Enter new Address: ");
                        contactInfo.setAddress(input.next().toString());
                        break;
                    case 3:
                        System.out.println("Enter new City: ");
                        contactInfo.setCity(input.next().toString());
                        break;
                    case 4:
                        System.out.println("Enter new State: ");
                        contactInfo.setState(input.next().toString());
                        break;
                    case 5:
                        System.out.println("Enter new Zip: ");
                        contactInfo.setZip(input.nextInt());
                        break;
                    case 6:
                        System.out.println("Enter new Phone Number: ");
                        contactInfo.setPhone_number(input.next().toString());
                        break;
                    case 7:
                        System.out.println("Enter new Email: ");
                        contactInfo.setEmail(input.next().toString());
                        break;
                }
                System.out.println("\nUpdated successfully!\n");
                break;
            } else
                System.out.println("\nNo First Name Found!\n");
        }
    }

    public void deleteContact(String addressBookName, Hashtable<String, ArrayList<contactInfo>> personInfoDict) {
        boolean found = false;
        boolean flag = findContact(addressBookName, personInfoDict);

        if (flag) {
            System.out.print("\nEnter the first name you want to delete : ");
            String fName = input.next();

            ArrayList<contactInfo> value = personInfoDict.get(addressBookName);
            for (int j = 0; j < value.size(); j++) {
                if (value.get(j).getFirst_name().equals(fName)) {
                    value.remove(j);
                    found = true;
                    break;
                }
            }
            if (found) {
                System.out.println("\nContact in Address Book Deleted.\n");
            } else
                System.out.println("\nNo such First Name found in the Address Book.\n");
        } else
            System.out.println("\nNo contacts found in the Address Book.\n");
    }

    public void displayCompanyContacts(Hashtable<String, ArrayList<contactInfo>> personInfoDict) {
        personInfoDict.keySet().forEach(entry -> System.out.println(entry + "->" + personInfoDict.get(entry) + "\n"));
    }

    /*Purpose : Using Java Streams to search for Person in a City or State across the multiple AddressBook.
                Maintain Dictionary of City and Person as well as State and Person
                Finally get the count of Persons by City or State using java streams
    */

    public void searchPerson() {
        Hashtable<String, Hashtable<String, ArrayList<String>>> hSearch = new Hashtable<>();
        AtomicInteger count = new AtomicInteger();

        System.out.println("Press 1 to search person by city");
        System.out.println("Press 2 to search person by state");
        int choice = input.nextInt();

        switch (choice) {
            case 1:
                System.out.print("\nEnter the city name to search: ");
                String cityName = input.next();

                personInfoDict.keySet().forEach(entry -> {
                    ArrayList<contactInfo> value = personInfoDict.get(entry);
                    List<String> city = value.stream().map(contactInfo::getCity).collect(Collectors.toList());
                    Hashtable<String, ArrayList<String>> person = new Hashtable<>();
                    ArrayList<String> firstName = new ArrayList<>();
                    for (int k = 0; k < city.size(); k++) {
                        if (city.get(k).equals(cityName)) {
                            firstName.add(value.get(k).getFirst_name());
                        }
                        person.put(cityName, firstName);
                    }
                    count.addAndGet(firstName.size());
                    hSearch.put(entry, person);
                });
                break;
            case 2:
                System.out.print("\nEnter the state name to search: ");
                String stateName = input.next();

                personInfoDict.keySet().forEach(entry -> {
                    ArrayList<contactInfo> value = personInfoDict.get(entry);
                    List<String> city = value.stream().map(contactInfo::getState).collect(Collectors.toList());
                    Hashtable<String, ArrayList<String>> person = new Hashtable<>();
                    ArrayList<String> firstName = new ArrayList<>();
                    for (int k = 0; k < city.size(); k++) {
                        if (city.get(k).equals(stateName)) {
                            firstName.add(value.get(k).getFirst_name());
                        }
                        person.put(stateName, firstName);
                    }
                    count.addAndGet(firstName.size());
                    hSearch.put(entry, person);
                });
                break;
        }
        System.out.println("\nViewing Persons by City or State\n" + hSearch);
        System.out.println("\nNumber of contact persons i.e. count by City or State is : " + count + "\n");
    }

    /*Purpose : Sort entries in the Address Book based on First Name alphabetically
                Sort entries in the Address Book based on City, State, or Zip
     */

    public void sortPerson() {
        System.out.println("Press 1 to sort person by First Name");
        System.out.println("Press 2 to sort person by City");
        System.out.println("Press 3 to sort person by State");
        System.out.println("Press 4 to sort person by Zip");
        int choice = input.nextInt();

        switch (choice) {
            case 1:
                System.out.println("\nSorting Address Book based on First Name");
                personInfoDict.keySet().forEach(entry -> {
                    List<contactInfo> data = personInfoDict.get(entry)
                            .stream().sorted(Comparator.comparing(contactInfo::getFirst_name))
                            .collect(Collectors.toList());
                    System.out.println(data);
                });
                break;
            case 2:
                System.out.println("\nSorting Address Book based on City");
                personInfoDict.keySet().forEach(entry -> {
                    List<contactInfo> data = personInfoDict.get(entry).stream()
                            .sorted(Comparator.comparing(contactInfo::getCity))
                            .collect(Collectors.toList());
                    System.out.println(data);
                });
                break;
            case 3:
                System.out.println("\nSorting Address Book based on State");
                personInfoDict.keySet().forEach(entry -> {
                    List<contactInfo> data = personInfoDict.get(entry).stream()
                            .sorted(Comparator.comparing(contactInfo::getState))
                            .collect(Collectors.toList());
                    System.out.println(data);
                });
                break;
            case 4:
                System.out.println("\nSorting Address Book based on Zip");
                personInfoDict.keySet().forEach(entry -> {
                    List<contactInfo> data = personInfoDict.get(entry).stream()
                            .sorted(Comparator.comparing(contactInfo::getZip))
                            .collect(Collectors.toList());
                    System.out.println(data);
                });
                break;
        }
    }
}

    © 2021 GitHub, Inc.
    Terms
    Privacy
    Security
    Status
    Docs

