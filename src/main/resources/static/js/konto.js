app.put('/account', (req, res) => {
    const { username } = req.session;
    const { email, password } = req.body;
  
    const updateQuery = 'UPDATE users SET email = ?, password = ? WHERE username = ?';
    connection.query(updateQuery, [email, password, username], (error) => {
      if (error) {
        console.error('Fehler beim Aktualisieren der Kontoinformationen:', error);
        res.sendStatus(500);
        return;
      }
  
      console.log('Kontoinformationen erfolgreich aktualisiert');
      res.sendStatus(200); // Erfolgsstatuscode
    });
  });

  app.delete('/account', (req, res) => {
    const { username } = req.session;
  
    
    const deleteQuery = 'DELETE FROM users WHERE username = ?';
    connection.query(deleteQuery, [username], (error) => {
      if (error) {
        console.error('Fehler beim Löschen des Benutzerkontos:', error);
        res.sendStatus(500);
        return;
      }
  
      console.log('Benutzerkonto erfolgreich gelöscht');
      
      req.session.destroy();
      res.sendStatus(200); // Erfolgsstatuscode
    });
  });
  