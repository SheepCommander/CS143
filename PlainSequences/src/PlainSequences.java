
// Jun Ong
// CS 143
//
// Use Collections.sort to sort the list of all substrings of a dna sequence.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PlainSequences implements Sequences {
    private List<String> sequences;
    private List<String> descriptions;
    private HashSet<Character> validBases;

    /**
     * post: Constructs a new instance of the PlainSequences class.
     * Initializes the internal lists for storing sequences and their descriptions.
     */
    public PlainSequences() {
        this.sequences = new ArrayList<String>();
        this.descriptions = new ArrayList<String>();
        Character[] arr = { 'A', 'C', 'G', 'T', 'U', 'R', 'Y', 'K', 'M', 'S', 'W',
                'B', 'D', 'H', 'V', 'N', 'X', '-' };
        validBases = new HashSet<>(Arrays.asList(arr)); // HashSet is faster for contains() lookup
    }

    /**
     * post: gives reference to descriptions
     */
    @Override
    public List<String> getDescriptions() {
        return descriptions;
    }

    /**
     * post: gives reference to sequences
     */
    @Override
    public List<String> getSequences() {
        return sequences;
    }

    /**
     * post: reads file into sequences & descriptions
     * 
     * @param fileName pre: should be a valid file
     */
    @Override
    public void readSequences(String fileName) throws FileNotFoundException {
        Scanner input = new Scanner(new File(fileName));
        String sequence = "";
        while (input.hasNextLine()) {
            sequence += input.nextLine();
        }
        descriptions.add(fileName); // Use the filename as a description
        sequences.add(sequence); // Add the sequence
        input.close();
    }

    /**
     * post: true if sequence contains only valid chars, false otherwise
     * 
     * @param index pre: index is valid
     */
    @Override
    public boolean isValidSequence(int index) {
        // https://en.wikipedia.org/wiki/International_Union_of_Pure_and_Applied_Chemistry#Amino_acid_and_nucleotide_base_codes
        // A A Adenine
        // C C Cytosine
        // G G Guanine
        // T T Thymine
        // U U Uracil
        // R A or G Purine
        // Y C, T or U Pyrimidines
        // K G, T or U Bases that are ketones
        // M A or C Bases with amino groups
        // S C or G Strong interaction
        // W A, T, or U Weak interaction
        // B Not A (i.e. C, G, T, or U) B comes after A
        // D Not C (i.e. A, G, T, or U) D comes after C
        // H Not G (i.e., A, C, T, or U) H comes after G
        // V Neither T nor U (i.e. A, C, or G) V comes after U
        // N A C G T U Nucleic acid
        // X Masked
        // - Gap of indeterminate length
        String sequence = sequences.get(index);
        for (int i = 0; i < sequence.length(); i++) {
            char dnaBase = sequence.charAt(i);
            if (!validBases.contains(dnaBase))
                return false;
        }
        return true;
    }

    /**
     * post: an accordingly formatted string of the sequence is returned
     * 
     * @param index         pre: index is a valid sequence
     * @param basesPerGroup pre: >0
     * @param groupsPerLine pre: >0
     */
    @Override
    public String formatInGroups(int index, int basesPerGroup, int groupsPerLine) {
        String sequence = sequences.get(index);
        String formatted = "";
        int groups = 0;
        for (int i = 0; i < sequence.length(); i++) {
            formatted += sequence.charAt(i);
            if ((i + 1) % basesPerGroup == 0) {
                formatted += " ";
                groups++;
                if (groups % groupsPerLine == 0) {
                    formatted += "\n";
                }
            }
        }
        return formatted;
    }

    /**
     * pre: readSequences called correctly and sequence index is valid
     * post: returns a map of the frequencies of every substring
     */
    @Override
    public Map<String, Integer> generateFrequencies(int index) {
        String sequence = sequences.get(index);
        Map<String, Integer> frequencies = new HashMap<>();
        for (int start = 0; start < sequence.length(); start++) {
            for (int end = start + 1; end <= sequence.length(); end++) {
                String sub = sequence.substring(start, end);
                frequencies.put(sub, frequencies.getOrDefault(sub, 0) + 1);
            }
        }
        return frequencies;
    }

    /**
     * pre: readSequences called correctly and sequence index valid
     * post: returns a list of substrings sorted alphabetically
     */
    @Override
    public List<String> getSortedListOfSubstrings(int index) {
        Map<String, Integer> frequencies = generateFrequencies(index);
        List<String> sortedList = new ArrayList<>(frequencies.keySet());
        Collections.sort(sortedList);
        return sortedList;
    }

    /**
     * pre: index is a valid index of a sequence
     * post: returns the sequence reversed, with each character changed to its complement
     */
    @Override
    public String getReverseComplement(int index) {
        String sequence = sequences.get(index);
        String reverse = "";
        System.out.println(sequence);

        // Add characters to stack
        List<Character> charList3 = sequence.chars()
                        .mapToObj(c -> (char) c)
                        .collect(java.util.stream.Collectors.toList()); // java..
        Stack<Character> stack = new Stack<>();
        stack.addAll(charList3);

        // Reverse them by processing their complements in reverse order
        while (!stack.isEmpty()) {
            char c = stack.pop();
            switch (c) {
                case 'T':
                    reverse += "A";
                    break;
                case 'A':
                    reverse += "T";
                    break;
                case 'G':
                    reverse += "C";
                    break;
                case 'C':
                    reverse += "G";
                    break;
            }
        }
        System.out.println(reverse);
        return reverse;
    }
}