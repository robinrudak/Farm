package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CropHandler {
    AnimalHandler AnimalHandler;

    public void setAnimalHandler(AnimalHandler animalHandler) {
        AnimalHandler = animalHandler;
    }
    ArrayList<Crop> cropList = new ArrayList<>();
    File cropFolder = new File("cropFolder");
    File cropFile = new File("cropFolder/cropFile.txt");

    public void viewCrops() {
        for (Crop crop : cropList) {
            System.out.println(crop.getDescription());
        }
    }

    public void removeCrops() {
        Scanner scanner = new Scanner(System.in);
        while (true){
            try {
                System.out.println("Delete with id: ");
                int id = Integer.parseInt(scanner.nextLine());
                boolean found = false;

                for(int i = 0; i < cropList.size(); i++) {
                    if (id == cropList.get(i).id) {
                        found = true;
                        cropList.remove(i);
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
    public void addCrops() {
        Scanner scanner = new Scanner(System.in);
        while (true){
            try {
                System.out.println("Add crop amount by id or type an id that does not exist to create a new crop: ");
                int id = Integer.parseInt(scanner.nextLine());
                boolean found = false;

                for (Crop crop : cropList) {
                    if (id == crop.id) {
                        found = true;
                        System.out.println("How much would you like to add?");
                        int addAmount = Integer.parseInt(scanner.nextLine());
                        crop.quantity += addAmount;
                        System.out.println("Crop with id " + id + " has added " + addAmount + " to its quantity!");
                        break;
                    }
                }

                if (!found) {
                    System.out.println("No item with that ID was found. Adding new crop!");
                    System.out.println("Name of crop: ");
                    String name = scanner.nextLine();
                    System.out.println("cropType: ");
                    String cropType = scanner.nextLine();
                    while (true){
                        try {
                            System.out.println("Quantity: ");
                            int quantity = Integer.parseInt(scanner.nextLine());
                            cropList.add(new Crop(name, cropType, quantity));
                            break;
                        } catch (Exception e){
                            System.out.println("That's not a number!");
                        }
                    }
                }
                break;
            } catch (Exception e){
                System.out.println("That's not a number!");
            }
        }
    }
    public void feedAnimal() {
        Scanner scanner = new Scanner(System.in);
        while (true){
            try {
                System.out.println("What animal would you like to feed? enter id: ");
                int id = Integer.parseInt(scanner.nextLine());
                boolean found = false;

                for(int i = 0; i < AnimalHandler.animalList.size(); i++) {
                    if (id == AnimalHandler.animalList.get(i).id) {
                        found = true;
                        while (true) {
                            try {
                                System.out.println("What crop would you like to feed? enter id: ");
                                int idCrop = Integer.parseInt(scanner.nextLine());
                                boolean foundCrop = false;
                                for (Crop crop : cropList) {
                                    if (idCrop == crop.id) {
                                        foundCrop = true;
                                        System.out.println("How much would you like to feed? ");
                                        int feedAmount = Integer.parseInt(scanner.nextLine());
                                        if (feedAmount <= crop.quantity) {
                                            crop.quantity -= feedAmount;
                                            System.out.println(AnimalHandler.animalList.get(i).name
                                                    + " has been feed: " + feedAmount + " " + crop.name);
                                            break;
                                        } else {
                                            System.out.println("Not enough food");
                                        }
                                    }
                                }
                                if (!foundCrop) {
                                    System.out.println("No item with that ID was found.");
                                }
                                break;
                            } catch (Exception e) {
                                System.out.println("That's not a number!");
                            }
                            break;
                        }}}
                if (!found) {
                    System.out.println("No item with that ID was found.");
                }
                break;
            } catch (Exception e){
                System.out.println("That's not a number!");
            }
        }
    }
    public CropHandler() {
        if (cropFile.exists()){
            load();
        } else {
            cropList.add(new Crop(1, "Ground Beef", "Meat", 30));
            cropList.add(new Crop(2, "Bird Food", "Seeds", 5000));
            cropList.add(new Crop(3, "Pig Food", "Feed", 10));
            cropList.add(new Crop(4, "Hamster Food", "Feed", 800));
        }


    }
    public void saveCrop() {
        try {
            cropFolder.mkdir();
            FileWriter fileWriter = new FileWriter(cropFile);
            BufferedWriter bf = new BufferedWriter(fileWriter);
            bf.write("id,name,crop type,quantity");
            for (Crop Crop: cropList) {
                bf.newLine();
                bf.write(Crop.getCSV());
            }
            bf.close();
        } catch (IOException e) {
            System.out.println("save failed");
        }
    }
    private void load() {
        try {
            FileReader fileReader = new FileReader(cropFile);
            BufferedReader bf = new BufferedReader(fileReader);

            bf.readLine();
            String line = bf.readLine();
            while (line != null) {
                String[] variables = line.split(",");

                int id = Integer.parseInt(variables[0]);
                String name = variables[1];
                String cropType = variables[2];
                int quantity = Integer.parseInt(variables[3]);

                Crop Crop = new Crop(id, name, cropType, quantity);
                cropList.add(Crop);

                line = bf.readLine();
            }

            bf.close();
        } catch (IOException e) {
            System.out.println("Load failed");
        }
    }
}
