package service;

import repository.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class JDBCUserService {

    private Connection getConnection() throws SQLException {

        String url = DbConstants.DB_CONNECTION_URL;
        String user = DbConstants.DB_USER;
        String password = DbConstants.DB_PASSWORD;
        return DriverManager.getConnection(url, user, password);
    }


    public int getUserId(String name) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            
            String selectQuery = "SELECT id FROM User WHERE name = ?";
            stmt = conn.prepareStatement(selectQuery);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            if( rs.next()){
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public int getSongId(String name) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            
            String selectQuery = "SELECT id FROM Song WHERE title = ?";
            stmt = conn.prepareStatement(selectQuery);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            if( rs.next()){
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
    
    public void showUser () {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String query = "SELECT * FROM User";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String status = rs.getString("status");
    
                System.out.println("ID: " + id + ", Name: " + name + ", Status: " + status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addUser (String name) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String query = "INSERT INTO User(name, status) VALUES (?, ?);";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, "Free");
            int rowsNow = stmt.executeUpdate();
            if (rowsNow > 0){
                System.out.println("User has been added!");
            }
            else{
                System.out.println("User not has been added!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String name) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            String query = "DELETE FROM User WHERE name = ?";
    
            stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            int rowsDeleted = stmt.executeUpdate();
    
            if (rowsDeleted > 0) {
                System.out.println("User '" + name + "' deleted successfully!");
            } else {
                System.out.println("User '" + name + "' not found or already deleted!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String seeStatus(String name) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            
            String selectQuery = "SELECT status FROM User WHERE name = ?";
            stmt = conn.prepareStatement(selectQuery);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("Status");
            } else {
                return "No status";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "No status";
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateUserStatus(String name) {
        Connection conn = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;
        String currentStatus = seeStatus(name);
        if (currentStatus != "No status")
        
        try {
            conn = getConnection();
            
            String newStatus = currentStatus.equals("Free") ? "Premium" : "Free";
                
            String updateQuery = "UPDATE User SET status = ? WHERE name = ?";
            updateStmt = conn.prepareStatement(updateQuery);
            updateStmt.setString(1, newStatus);
            updateStmt.setString(2, name);
            int rowsUpdated = updateStmt.executeUpdate();                
            if (rowsUpdated > 0) {
                System.out.println("User '" + name + "' status updated from '" + currentStatus + "' to '" + newStatus + "' successfully!");
            } else {
                System.out.println("User '" + name + "' not found!");
               }
            
                
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (selectStmt != null) {
                    selectStmt.close();
                }
                if (updateStmt != null) {
                    updateStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void addArtist (String name) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String query = "INSERT INTO Artist(name) VALUES (?);";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            int rowsNow = stmt.executeUpdate();
            if (rowsNow > 0){
                System.out.println("Artist has been added to the database!");
            }
            else{
                System.out.println("Artist not has been added to the database!");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSong (String artist_name, String song_name, String genre_name) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String query = "SELECT id FROM artist WHERE name = ?";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, artist_name);
            rs = stmt.executeQuery();
            if( rs.next()){
                int id = rs.getInt("id");

                query = "INSERT INTO Song(title, genre, artist_id) Values (?, ?, ?)";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, song_name);
                stmt.setString(2, genre_name);
                stmt.setInt(3, id);
                int rowsAdded = stmt.executeUpdate();
                if (rowsAdded > 0){
                    System.out.println("Song has been added to the database!");
                }
                
            }

        }catch (SQLException e) {
        e.printStackTrace();
        }
    }

    public void addAlbums (String artist_name,String title,int year) {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String query = "SELECT id FROM artist WHERE name = ?";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, artist_name);
            rs = stmt.executeQuery();
            if( rs.next()){
                int id = rs.getInt("id");

                query = "INSERT INTO Album(title, year, artist_id) Values (?, ?, ?)";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, title);
                stmt.setInt(2, year);
                stmt.setInt(3, id);
                int rowsAdded = stmt.executeUpdate();
                if (rowsAdded > 0){
                    System.out.println("Album has been added to the database!");
                }
                
            }

        }catch (SQLException e) {
        e.printStackTrace();
        }
    }

    public void showArtists () {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String query = "SELECT * FROM Artist";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
    
                System.out.println("ID: " + id + ", Name: " + name );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addToLikedSongs(String user, String song) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int userId = getUserId(user);
        int songId = getSongId(song);


        try {
            conn = getConnection();
            String query = "INSERT INTO Liked_Songs (user_id, song_id) VALUES (?, ?)";

            stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, songId);
            int rowsAdded = stmt.executeUpdate();
            
                if (rowsAdded > 0){
                    System.out.println("Row has been added to the database!");
                }
                

        }catch (SQLException e) {
        e.printStackTrace();
        }
    }

    public void showLikedSongs(String user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int userId = getUserId(user);
        try {
            conn = getConnection();
            String query = "SELECT * FROM Liked_Songs WHERE user_Id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("song_id");
    
                System.out.println("User ID: " + id + ", Song ID: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showNumberOfPremiums() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            
            String selectQuery = "SELECT count(*) FROM User WHERE status = ?";
            stmt = conn.prepareStatement(selectQuery);
            stmt.setString(1, "Premium");
            rs = stmt.executeQuery();
            if( rs.next()){
                int nr = rs.getInt(1);
                System.out.println("Number of premiums: ");
                System.out.println(nr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

}
