import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author dmifed
 */

public class PathFinder {

    private List<String> paths;
    private List<String> names;

    private final String tab = "\t";
    private File root;

    public PathFinder(File root) {
        paths = new ArrayList<>();
        names = new ArrayList<>();
        this.root = root;
    }


    public Map<String, List<String>> getMapOfListFilesInEachFolder(File root, Map<String, List<String>> mapOfListFilesInEachFolder){
        if(!root.isFile() && root.getName().startsWith("S")){
            String imageFolder = root.getAbsolutePath();
            File[] files = root.listFiles();

            List<String> imagePaths = new ArrayList<>();
            if(files != null){
                if(files.length > 10) System.out.println(root.getAbsolutePath());
                for(File file : files) {
                    imagePaths.add(file.getName());
                }
            }
            mapOfListFilesInEachFolder.put(imageFolder, imagePaths);
        }else {
            File[] files = root.listFiles();
            if(files != null){
                for(File file : files) {
                    getMapOfListFilesInEachFolder(file, mapOfListFilesInEachFolder);
                }
            }
        }
        return mapOfListFilesInEachFolder;
    }

    public void checkSequence(File root){
        if(!root.isFile() && root.getName().startsWith("S")){
            File[] files = root.listFiles();
            if(files != null){
                List<String> images = new ArrayList<>();
                for(File f : files){
                    String fileType = f.getAbsolutePath().substring(f.getAbsolutePath().length()-4) ;
                    if(fileType.equalsIgnoreCase(".jpg") ||
                        fileType.equalsIgnoreCase(".tif") ||
                        fileType.equalsIgnoreCase(".png") ||
                        fileType.equalsIgnoreCase("jpeg")){
                        images.add(f.getName());
                    }
                }
                List<Integer> numbers = new ArrayList<>();
                for(String s : images){
                    int n = Integer.parseInt(s.split("_")[0]);
                    numbers.add(n);
                }
                numbers.sort(Comparator.naturalOrder());
                for(int i = 1; i < numbers.size(); i++){
                    if(numbers.get(i-1) + 1 != numbers.get(i)){
                        System.out.println(root.getAbsolutePath() + " " + images);
                    }
                }
            }
        }else {
            File[] files = root.listFiles();
            if(files != null){
                for(File file : files) {
                    checkSequence(file);
                }
            }
        }
    }

    public Map<String, List<String>> getMapOfPairsImages(Map<String, List<String>> mapOfListFilesInEachFolder) throws Exception{
        Map<String, List<String>> mapOfPairsImages = new TreeMap<>();
        for(Map.Entry<String, List<String>> entry : mapOfListFilesInEachFolder.entrySet()){
            String folder = entry.getKey();
            List<String> imagesInFolder = entry.getValue();
            if(imagesInFolder.size()>1){
                List<String> pairs = pairImages(imagesInFolder);
                if(pairs.size() > 0 && pairs.size()%2 != 0) throw new Exception("pairs.size()%2 != 0");
                if(pairs.size() >= 2){
                    mapOfPairsImages.put(folder, pairs);
                }

            }
        }
        return mapOfPairsImages;
    }

    private List<String> pairImages(List<String> images){
        List<String> pairs = new ArrayList<>();
        Set<String> setStartNames = new HashSet<>();
        for(String s : images) {
            String nameStarts = s.substring(0, 2);
            if(!setStartNames.add(nameStarts)){
                for(String imageName : images){
                    if(imageName.substring(0,2).equals(nameStarts)){
                        pairs.add(tab + imageName);
                    }
                }
            }
        }
        return pairs;
    }

    public void saveMap(Map<String, List<String>> mapOfPairsImages){
        Path path = Paths.get(root.getAbsolutePath() + "/txt/pairImages.txt");
        if(path.toFile().exists()){
            path.toFile().delete();
        }
        try {
            Files.createFile(path);
            PrintWriter printWriter = new PrintWriter(path.toFile().getAbsolutePath());
            for(Map.Entry<String, List<String>> entry : mapOfPairsImages.entrySet()){
                String folder = entry.getKey();
                List<String> imagesInFolder = entry.getValue();
                printWriter.print(folder);
                printWriter.print("\r\n");
                for(String s : imagesInFolder){
                    printWriter.print(s);
                    printWriter.print("\r\n");
                }
                printWriter.print("\r\n");
            }
            printWriter.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void saveMapOfListFilesInEachFolderToFile(Map<String, List<String>> mapOfListFilesInEachFolder){
        Path path = Paths.get(root.getAbsolutePath() + "/txt/allFiles.txt");
        if(path.toFile().exists()){
            path.toFile().delete();
        }
        System.out.println("saving to" + path.toString() + " " + mapOfListFilesInEachFolder.values().size() + " strings");
        try {
            Files.createFile(path);
            PrintWriter printWriter = new PrintWriter(path.toFile().getAbsolutePath());
            for(Map.Entry<String, List<String>> entry : mapOfListFilesInEachFolder.entrySet()){
                String folder = entry.getKey();
                List<String> imagesInFolder = entry.getValue();
                printWriter.print(folder);
                printWriter.print("\r\n");
                for(String s : imagesInFolder){
                    printWriter.print(s);
                    printWriter.print("\r\n");
                }
            }
            printWriter.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<String> allImages(File root){
        return null;
    }


}
