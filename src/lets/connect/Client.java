package lets.connect;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.*;//for calender
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import static lets.connect.Server.f;
import static lets.connect.Server.formatLabel;
import static lets.connect.Server.vertical;

  
public class Client  implements ActionListener{
    
    JTextField text1;
    static JPanel a1;
    static Box vertical=Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f=new JFrame();
    
    
    Client(){
        f.setLayout(null);
        
        JPanel p1=new JPanel();
        p1.setBackground(new Color(0,127,102));
        p1.setBounds(0, 0,450, 70);
        p1.setLayout(null);
        f.add(p1);
        
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        //for scaling the image
        
        Image i2=i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);//bcoz we cant directly pass i2 into Jlable class
        JLabel back=new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);
        
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });
       
        //for profile setting
        
        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/donku.png"));
        //for scaling the image
        Image i5=i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);//bcoz we cant directly pass i2 into Jlable class
        JLabel profile=new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);
        
        //for video calling
        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8=i7.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);//bcoz we cant directly pass i2 into Jlable class
        JLabel video=new JLabel(i9);
        video.setBounds(340, 30,20, 20);
        p1.add(video);
        
        
        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("icons/R.png"));
        Image i11=i10.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon(i11);//bcoz we cant directly pass i2 into Jlable class
        JLabel phone=new JLabel(i12);
        phone.setBounds(380, 30,20, 20);
        p1.add(phone);
        
        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("icons/m.png"));
        Image i14=i13.getImage().getScaledInstance(5, 20, Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon(i14);//bcoz we cant directly pass i2 into Jlable class
        JLabel morevert=new JLabel(i15);
        morevert.setBounds(420, 32,5, 20);
        p1.add(morevert);
        
        //displaying name
        JLabel name=new JLabel("Monku");
        name.setBounds(110, 15,100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);
        
        JLabel status=new JLabel("Active...");
        status.setBounds(110, 45,100, 13);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,13));
        p1.add(status);
        
        //typing area
        a1=new JPanel();
        a1.setBounds(5, 75, 440, 470);
        f.add(a1);
        
        //typing text field
        text1=new JTextField();
        text1.setBounds(5,550,310,40);
        text1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(text1);
        
       // send button
        JButton send=new JButton("Send");
        send.setBounds(320,550,125,40);
        send.setBackground(new Color(0,127,100));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF",Font.BOLD,16));
        f.add(send);
        
        //whole screen 
        f.setSize(450,600);
        f.setLocation(800,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
   }
    
    //inbuilt method of actionlistener that we had implemented
    @Override
   public void actionPerformed(ActionEvent e){
      
       try{
           String out=text1.getText();
                
      JPanel p2=formatLabel(out);
     //bcoz we cannot assign string directly to jpanel of line no 122 
            
      a1.setLayout(new BorderLayout());
      
      JPanel right=new JPanel(new BorderLayout());
      right.add(p2, BorderLayout.LINE_END);
      vertical.add(right);
      vertical.add(Box.createVerticalStrut(10));
      
      a1.add(vertical, BorderLayout.PAGE_START);
      
      dout.writeUTF(out);
      
      text1.setText("");
      
      f.repaint();
      f.invalidate();
      f.validate();
      System.out.println(out);
   
    }catch(Exception ex){
          ex.printStackTrace();
      }
      
   }
   
   public static JPanel formatLabel(String out){
       JPanel panel=new JPanel();
       panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
       
       //JLabel output=new JLabel("<html><p style=\"width:150px\">"+ out +"</p></html>");
       JLabel output=new JLabel(out);
       output.setFont(new Font("Tohoma",Font.PLAIN,16));
       output.setBackground(new Color(255,218,185));
       output.setOpaque(true);
       output.setBorder(new EmptyBorder(15,15,15,20));
       
       panel.add(output);
       
       Calendar cal=Calendar.getInstance();
       SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
       
       JLabel time=new JLabel();
       time.setText(sdf.format(cal.getTime()));
       
       panel.add(time);
             
       return panel;
   }
    
   public static void main(String  args[]){
       new Client();
       
       try{
          Socket s=new Socket("127.0.0.1",6001); 
          
          DataInputStream din=new DataInputStream(s.getInputStream());//for accepting the msg
          dout=new DataOutputStream(s.getOutputStream());//for sending msg
              
          while(true){
              a1.setLayout(new BorderLayout());
                   String msg=din.readUTF();
                   JPanel panel=formatLabel(msg);
                   
                   JPanel left=new JPanel(new BorderLayout());
                   left.add(panel,BorderLayout.LINE_START);
                   vertical.add(left);
                   
                   vertical.add(Box.createVerticalStrut(15));
                   a1.add(vertical,BorderLayout.PAGE_START);
                   f.validate();
               }

       }catch(Exception e){
           e.printStackTrace();
       }
   } 
}


