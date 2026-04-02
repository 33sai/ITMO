package utilities;

import models.MusicBand;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is used for managing a Hashtable.
 */
public class CollectionManager {
    private Hashtable<String, MusicBand> collection;
    private Date initializationDate;

    /**
     * Constructs a collection manager that initializes the private fields.
     */
    public CollectionManager() {
        this.collection = new Hashtable<>();
        this.initializationDate = new Date();
    }

    /**
     * This method is used for putting a key-value pair to the Hashtable.
     * @param key  they key of the Hashtable.
     * @param band  the value of the Hashtable as {@link MusicBand}.
     */
    public void add(String key, MusicBand band) {
        this.collection.put(key, band);
    }

    /**
     * This method is used to retrieve a music band that matches the key of the collection.
     * @param key  the key to retrieve a specific music band.
     * @return  the music band that is found, returns null if not found.
     */
    public MusicBand get(String key) {
        return this.collection.get(key);
    }

    /**
     * This method is used to remove a certain music band from the collection.
     * @param key  the key that is tied to the music band to be removed.
     */
    public void remove(String key) {
        this.collection.remove(key);
    }

    /**
     * This method is used to empty the collection.
     */
    public void clear() {
        this.collection.clear();
    }

    /**
     * This method is used to get all the keys of the collection.
     * @return  the keys as a {@link Set<String>}.
     */
    public Set<String> getKeys() {
        return this.collection.keySet();
    }


    /**
     * This method is used to get all the music bands from the collection.
     * @return  all music bands as {@link Collection<MusicBand>}.
     */
    public Collection<MusicBand> getAll() {
        return this.collection.values();
    }


    /**
     * This method is used to get the size of the collection.
     * @return  the size of the collection as int.
     */
    public int size() {
        return this.collection.size();
    }

    /**
     * This method is used to get the date the collection was created.
     * @return  the date of creation.
     */
    public Date getInitializationDate() {
        return this.initializationDate;
    }

    /**
     * This method is used to get the type of the collection.
     * @return  the type of the collection.
     */
    public String getCollectionType() {
        return this.collection.getClass().getSimpleName();
    }

    /**
     * This method is used to get the collection itself.
     * @return  the collection.
     */
    public Hashtable<String, MusicBand> getCollection() {
        return this.collection;
    }

    /**
     * This method is used to check if this collection contains a specific key.
     * @param key  they key to check.
     * @return  true if it exists, otherwise false.
     */
    public boolean containsKey(String key) {
        return this.collection.containsKey(key);
    }


    /**
     * Returns all bands with the given number of participants.
     *
     * @param count  the number of participants to filter by.
     * @return The list of music band objects matching the parameter count.
     */
    public List<MusicBand> getBandsByParticipants(long count) {
        return this.collection.values().stream()
                .filter(band -> band.getNumberOfParticipants() == count)
                .collect(Collectors.toList());
    }

    /**
     * Return all non-null singles count in descending order.
     * @return  The list of Integers in descending order.
     */

    public List<Integer> getAllSinglesCountDescending() {
        return this.collection.values().stream()
                .map(MusicBand::getSinglesCount)
                .filter(Objects::nonNull)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

    }

    /**
     * Groups bands by name and returns how many bands had the same name.
     *
     * @return Map<String, Long> where String is the name of the band and Long is the count.
     */
    public Map<String, Long> countByName() {
        return this.collection.values().stream()
                .collect(Collectors.groupingBy(
                        MusicBand::getName,
                        Collectors.counting()
                ));

    }

    /**
     * Finds a band by its id.
     * @param id The id to search for.
     * @return the music band that was found, null if not found.
     */
    public MusicBand getBandById(long id) {
        return this.collection.values().stream()
                .filter(band -> band.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * A custom implementation of the toString() method.
     * @return the data of the collection as String.
     */
    @Override
    public String toString() {
        if (this.collection.isEmpty()) {
            return "The collection is empty.";
        }
        StringBuilder output = new StringBuilder();
        for (String key: this.collection.keySet()) {
            output.append(this.collection.get(key)).append("\n").append("=".repeat(100)).append("\n");
        }
        return output.toString();
    }


}
