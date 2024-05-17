
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class StudentSurnameStorage {
    private TreeMap<String, Set<Long>> surnamesTreeMap = new TreeMap<>();

    public void studentCreated(Long id, String surname){
        Set<Long> existingIds = surnamesTreeMap.getOrDefault(surname, new HashSet<>());
        existingIds.add(id);
        surnamesTreeMap.put(surname, existingIds);
    }

    public void studentDeleted(Long id, String surname){
        surnamesTreeMap.get(surname).remove(id);
    }

    public void studentUpdated(Long id, String oldSurname, String newSurname){
        studentDeleted(id, oldSurname);
        studentCreated(id, newSurname);
    }

    public Set<Long> getStudentsBySurnamesLessOrEqualThan(String surname){
        if (surname.isEmpty()) {//Если строка пустая
            Set<Long> res = surnamesTreeMap
                    .values()
                    .stream()
                    .flatMap(longs -> longs.stream())
                    .collect(Collectors.toSet());
            return res;
        } else if(surname.indexOf(',') != -1){//Если в строке находятся две фамилии
            String[] surnames = surname.split(",");
            Set<Long> res = surnamesTreeMap.subMap(surnames[0], true, surnames[1], true)
                    .values()
                    .stream()
                    .flatMap(longs -> longs.stream())
                    .collect(Collectors.toSet());
            return res;
        }else {//В случае одной фамилии
            Set<Long> res = surnamesTreeMap.get(surname);
            return res;
        }
    }
}
