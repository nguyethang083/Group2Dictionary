package Dictionary.Features;

public class StringProcessing {
    public static String normalizeString(String searchTerm) {
        if (searchTerm.length() < 2) return searchTerm;
        searchTerm = searchTerm.toLowerCase();
        searchTerm = searchTerm.strip();
        searchTerm = searchTerm.trim();
        searchTerm = searchTerm.substring(0, 1).toUpperCase() + searchTerm.substring(1);
        return searchTerm;
    }

    public static String normalizeStringForSearch(String searchTerm) {
        StringBuilder stringBuilder = new StringBuilder(searchTerm);
        int index = stringBuilder.indexOf(";");
        if (index != -1) {
            stringBuilder.delete(index, stringBuilder.length());
        }
        searchTerm = stringBuilder.toString();
        System.out.println(searchTerm);
        return searchTerm;
    }

    public static String normalizeStringForVoice(String searchTerm) {
        StringBuilder stringBuilder = new StringBuilder(searchTerm);
        int index = stringBuilder.indexOf("\n");
        if (index != -1) {
            stringBuilder.delete(index, stringBuilder.length());
        }
        searchTerm = stringBuilder.toString();
        System.out.println(searchTerm);
        return searchTerm;
    }


}
