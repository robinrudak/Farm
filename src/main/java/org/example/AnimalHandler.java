package org.example;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AnimalHandler {
    ArrayList<Animal> animalList = new ArrayList<>();
    File animalFolder = new File("animalFolder");
    File animalFile = new File("animalFolder/animalFile.txt");
    public void viewAnimals() {
        for (Animal animal : animalList) {
            System.out.println(animal.getDescription());
        }
    }
    public void addAnimal() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Name of animal: ");
        String name = scanner.nextLine();
        System.out.println("Type of species: ");
        String species = scanner.nextLine();
        animalList.add(new Animal(name, species));
    }
    public void removeAnimal()  {
        Scanner scanner = new Scanner(System.in);
        while (true){
            try {
                System.out.println("Delete with id: ");
                int id = Integer.parseInt(scanner.nextLine());
                boolean found = false;

                for(int i = 0; i < animalList.size(); i++) {
                    if (id == animalList.get(i).id) {
                        found = true;
                        animalList.remove(i);
                        i--;
                        System.out.println("Crop with id " + id + " has been removed!");
                    }
                }

                if (!found) {
                    System.out.println("No item with that ID was found.");

                }
                break;
            } catch (Exception e){
                System.out.println("That's not a number!");
            }}}
    public AnimalHandler() {
        if (animalFile.exists()) {
            load();
        }   else {
            animalList.add(new Animal(1, "Bengt", "Lion"));
            animalList.add(new Animal(2, "Sven-Uggla", "Owl"));
            animalList.add(new Animal(3, "Stig", "Pig"));
            animalList.add(new Animal(4, "Jonas", "Hamster"));
        }
    }
    private void load() {
        try {
            FileReader fileReader = new FileReader(animalFile);
            BufferedReader bf = new BufferedReader(fileReader);

            bf.readLine();
            String line = bf.readLine();
            while (line != null) {
                String[] variables = line.split(",");

                int id = Integer.parseInt(variables[0]);
                String name = variables[1];
                String species = variables[2];

                Animal Animal = new Animal(id, name, species);
                animalList.add(Animal);

                line = bf.readLine();
            }

            bf.close();
        } catch (IOException e) {
            System.out.println("Load Failed");
        }
    }
    public void saveAnimal() {
        try {
            animalFolder.mkdir();
            FileWriter fileWriter = new FileWriter(animalFile);
            BufferedWriter bf = new BufferedWriter(fileWriter);
            bf.write("id,name,Species");
            for (Animal Animal: animalList) {
                bf.newLine();
                bf.write(Animal.getCSV());
            }
            bf.close();
        } catch (IOException e) {
            System.out.println("save failed");
        }
    }

}
