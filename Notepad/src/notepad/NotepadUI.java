package notepad;
import javax.swing.*;
import java.io.*;

public class NotepadUI extends javax.swing.JFrame {

    public NotepadUI() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
                         
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Untitled");
        setIconImages(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setText("New");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem2.setText("Open");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem3.setText("Save");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem4.setText("Save As");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
        );

        pack();
    }                     
    
    private boolean cancelButtonClicked = false;
    private String loadedContent = "";
    private File selectedFile;
    
    private void ExitChecker(){
        String currentContent = jTextArea2.getText().trim();
        if (!currentContent.isEmpty() && !currentContent.equals(loadedContent)){   
            String[] options = {"Save", "Don't Save", "Cancel"};
            int selected_option = JOptionPane.showOptionDialog(null,"Do you want to save changes to " + getTitle()+"?", "Save Changes", 
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,options, options[0]);
            
            switch (selected_option){
            case JOptionPane.YES_OPTION:
                Save();
                jTextArea2.setText("");
                setTitle("Untitled");
                selectedFile=null;
                break;
            case JOptionPane.NO_OPTION:
                jTextArea2.setText("");
                setTitle("Untitled");
                selectedFile=null;
                break;
            case JOptionPane.CANCEL_OPTION:
                cancelButtonClicked = true;
                break;
            default:
                cancelButtonClicked = true;
            }
        }
    }
    
    private void SaveAs(){
        if (!jTextArea2.getText().trim().isEmpty()){
            JFileChooser Folder = new JFileChooser();
            Folder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            Integer opt = Folder.showSaveDialog(this);     
            if (opt == JFileChooser.APPROVE_OPTION){
                File selectedDirectory = Folder.getSelectedFile();
                String filename = JOptionPane.showInputDialog(this, "Enter the filename:", "Save As", JOptionPane.PLAIN_MESSAGE);
                if (filename != null && !filename.trim().isEmpty()) {
                    int lastDotIndex = filename.lastIndexOf(".");
                    if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
                        filename += ".txt";
                    }
                    File outputFile = new File(selectedDirectory, filename);
                    try {
                        FileWriter fileWriter = new FileWriter(outputFile);
                        fileWriter.write(jTextArea2.getText());
                        fileWriter.close();
                        loadedContent= jTextArea2.getText();
                        setTitle(filename); 
                        JOptionPane.showMessageDialog(this, "File saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "An error occurred while saving the file.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid filename. File was not saved.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }    
        }
    }
    
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        ExitChecker();
        String currentContent = jTextArea2.getText().trim();
        if (currentContent.equals(loadedContent)){
            jTextArea2.setText("");
            setTitle("Untitled");
            selectedFile=null;
        }
    }                                          
    
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        JFileChooser fileChooser = new JFileChooser();
        int file_result = fileChooser.showOpenDialog(this);
        
        if (file_result == JFileChooser.APPROVE_OPTION){
            selectedFile = fileChooser.getSelectedFile();
            try{
                loadedContent = readFile(selectedFile);
                jTextArea2.setText(loadedContent);
                setTitle(selectedFile.getName());
            } catch (IOException e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error reading file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }                                          

    private String readFile(File file) throws IOException{
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                if (reader.ready()){
                    content.append("\n");
                }
            }
        }
        return content.toString();
    }
    
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        SaveAs();
    }                                          

    private void formWindowClosing(java.awt.event.WindowEvent evt) {                                   
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ExitChecker();     
        if (cancelButtonClicked) {
            cancelButtonClicked = false; 
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        } 
    }                                  

    private void Save(){
        if (selectedFile != null && !jTextArea2.getText().trim().isEmpty()){
            try {
            FileWriter myWriter = new FileWriter(selectedFile);
            myWriter.write(jTextArea2.getText());
            myWriter.close();
            loadedContent = jTextArea2.getText();
            JOptionPane.showMessageDialog(this, "File saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An Error occured.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else{
            SaveAs();
        } 
    }
    
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        Save();
    }                                          

    public static void main(String args[]) {
       
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NotepadUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NotepadUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NotepadUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NotepadUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NotepadUI().setVisible(true);
            }
        });
    }

                       
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea2;
                   
}
