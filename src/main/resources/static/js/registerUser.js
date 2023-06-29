import { createConnection } from 'mysql';
const connection = createConnection({
  host: '127.0.0.1', 
  user: 'root', 
  password: 'james2', 
  database: 'registration'
});

function registerUser(username, email, password) {
  
  const checkQuery = 'SELECT COUNT(*) AS count FROM users WHERE username = ? OR email = ?';
  connection.query(checkQuery, [username, email], (error, results) => {
    if (error) {
      console.error('Fehler bei der Überprüfung des Benutzernamens/E-Mail:', error);
      
      return;
    }

    const count = results[0].count;
    if (count > 0) {
      console.log('Benutzername oder E-Mail ist bereits vorhanden');
      
      return;
    }

    
    const insertQuery = 'INSERT INTO users (username, email, password) VALUES (?, ?, ?)';
    connection.query(insertQuery, [username, email, password], (error) => {
      if (error) {
        console.error('Fehler beim Einfügen des Benutzers in die Datenbank:', error);
        
        return;
      }

      console.log('Benutzer erfolgreich registriert');

    });
  });
}

registerUser('john.doe', 'john@example.com', 'passw0rd');
