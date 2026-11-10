package databases.part03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import databases.part02.Artist;

public class AlbumDAO {

    private final String connectionString;

    public AlbumDAO(String connectionString) {

        this.connectionString = connectionString;
    }

    public List<Album> getAlbumsByArtist(Artist artist) {

        List<Album> albums = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(connectionString);

            preparedStatement = connection.prepareStatement(

                    "SELECT AlbumId, Title, ArtistId FROM Album WHERE ArtistId = ? ORDER BY AlbumId ASC");

            preparedStatement.setLong(1, artist.getId());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                long albumId = resultSet.getLong("AlbumId");
                String title = resultSet.getString("Title");
                long artistId = resultSet.getLong("ArtistId");

                albums.add(new Album(albumId, title, artistId));
            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            try {

                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }

        return albums;
    }

    public boolean addAlbum(Album album) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(connectionString);

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO Album (Title, ArtistId) VALUES (?, ?)");
            preparedStatement.setString(1, album.getTitle());
            preparedStatement.setLong(2, album.getArtistId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean updateAlbum(Album album) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(connectionString);

            preparedStatement = connection.prepareStatement(
                    "UPDATE Album SET Title = ?, ArtistId = ? WHERE AlbumId = ?");
            preparedStatement.setString(1, album.getTitle());
            preparedStatement.setLong(2, album.getArtistId());
            preparedStatement.setLong(3, album.getId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean deleteAlbum(Album album) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(connectionString);

            preparedStatement = connection.prepareStatement(
                    "DELETE FROM Album WHERE AlbumId = ?");
            preparedStatement.setLong(1, album.getId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}