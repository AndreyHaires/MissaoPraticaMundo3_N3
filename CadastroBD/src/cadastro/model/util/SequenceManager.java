package cadastro.model.util;
/**
 *
 * @author Andrey H Aires
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceManager {
    // Metodo para obter o próximo valor de uma sequencia
    public static int getValue(String sequenceName) {
        int nextValue = -1;

        String sql = "SELECT NEXTVAL(?) AS next_value";

        try (Connection connection = ConectorBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, sequenceName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    nextValue = resultSet.getInt("next_value");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return nextValue;
    }
}
