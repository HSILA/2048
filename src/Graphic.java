import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
 * Created by ali on 3/27/17.
 */
public class Graphic extends Game {
    //Defining variables
    private int hs;
    private int time_sec = 0;
    private int fWidth = 394;
    private int fHeight = 326;
    private int boardSize = 305;
    private int edge = 25;
    //
    private Color bgColor = new Color(130, 185, 224);
    private Font number = new Font("Arial", Font.BOLD, 22);
    Board gb = new Board();
    private JFrame f = new JFrame();
    private JPanel up = new JPanel();
    private JPanel leftEdge = new JPanel();
    private JPanel rightEdge = new JPanel();
    private JPanel downEdge = new JPanel();

    private JLabel score;
    private JLabel moves;
    private JLabel state;
    private JLabel highScore;
    private JButton restart = new JButton();
    private ImageIcon res;

    public Graphic() throws IOException {
        //Main game frame
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setSize(fWidth, fHeight);
        f.setTitle("2048 Game");
        f.addKeyListener(gb);
        refreshHighScore();
        writer(hs);

        //Upper panel :
        up.setLayout(new GridLayout(2, 2));
        up.setPreferredSize(new Dimension(fWidth, 120));
        up.setBackground(bgColor);
        //Game label (2048)
        JLabel gLabel = new JLabel("2048", SwingConstants.CENTER);
        gLabel.setFont(new Font("Bombing", Font.BOLD, 50));
        up.add(gLabel);
        //Timer label
        JLabel timer = new JLabel("Time : 00:00:00", SwingConstants.CENTER);
        timer.setFont(new Font("Arial", Font.BOLD, 18));
        up.add(timer);
        //Score Label
        score = new JLabel("Score : " + Score, SwingConstants.CENTER);
        score.setFont(new Font("Arial", Font.BOLD, 18));
        up.add(score);
        //High Score Label
        highScore = new JLabel("High Score :" + hs, SwingConstants.CENTER);
        highScore.setFont(new Font("Arial", Font.BOLD, 18));
        up.add(highScore);

        //Defining left , right & down edges
        leftEdge.setPreferredSize(new Dimension(edge, boardSize));
        leftEdge.setBackground(bgColor);

        rightEdge.setPreferredSize(new Dimension(edge, boardSize));
        rightEdge.setBackground(bgColor);

        downEdge.setPreferredSize(new Dimension(fWidth, (5 / 2) * edge));
        downEdge.setBackground(bgColor);
        downEdge.setLayout(new GridLayout(1, 3));

        moves = new JLabel("Moves : " + tileMoves, SwingConstants.CENTER);
        moves.setFont(new Font("Arial", Font.BOLD, 22));
        state = new JLabel("Keep Playing", SwingConstants.CENTER);
        state.setFont(new Font("Arial", Font.PLAIN, 18));
        //Restart Button
        res = new ImageIcon(".//src//res.png");
        restart.setText("");
        restart.setFocusable(false);
        restart.setIcon(res);
        restart.setBackground(bgColor);
        restart.setBorderPainted(false);
        restart.setPreferredSize(new Dimension(64, 64));
        restart.addActionListener(actionEvent -> {
            res();
            time_sec = 0;
            try {
                refreshLabel(Score, tileMoves, hs);
            } catch (IOException e) {
                e.printStackTrace();
            }
            gb.repaint();
        });
        downEdge.add(moves);
        downEdge.add(restart);
        downEdge.add(state);

        //Add panels to frame
        f.getContentPane().add(up, BorderLayout.NORTH);
        f.getContentPane().add(downEdge, BorderLayout.SOUTH);
        f.getContentPane().add(leftEdge, BorderLayout.WEST);
        f.getContentPane().add(rightEdge, BorderLayout.EAST);
        f.getContentPane().add(gb, BorderLayout.CENTER);
        f.pack();

        //Timer
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        time_sec++;
                        String time_str = "";
                        int hour = time_sec / 3600;
                        int min = (time_sec % 3600) / 60;
                        int sec = time_sec % 60;
                        if (hour > 9) {
                            time_str += hour + ":";
                        } else {
                            time_str += "0" + hour + ":";
                        }
                        if (min > 9) {
                            time_str += min + ":";
                        } else {
                            time_str += "0" + min + ":";
                        }
                        if (sec > 9) {
                            time_str += sec;
                        } else {
                            time_str += "0" + sec;
                        }
                        timer.setText("Time : " + time_str);
                    } catch (Exception ignored) {
                    }
                }
            }
        }).start();

    }

    private class Board extends JPanel implements KeyListener {
        //344*342
        @Override
        protected void paintComponent(Graphics g) {
            int xi = this.getWidth();
            int yi = this.getHeight();
            g.setColor(Color.lightGray);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.black);
            g.drawRect(0, 0, xi - 1, yi - 1);

            g.drawLine(xi / 2, 0, xi / 2, yi);
            g.drawLine(0, yi / 2, xi, yi / 2);
            g.drawLine(xi / 4, 0, xi / 4, yi);
            g.drawLine(0, yi / 4, xi, yi / 4);
            g.drawLine(0, 3 * yi / 4, xi, 3 * yi / 4);
            g.drawLine(3 * xi / 4, 0, 3 * xi / 4, yi);

            //Drawing numbers and filling colors:
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    switch (tiles[y][x]) {
                        case 1:
                            g.setColor(new Color(238, 228, 218));
                            g.fillRect(1 + x * xi / 4, 1 + y * yi / 4, (xi / 4) - 2, (yi / 4) - 2);
                            g.setColor(Color.black);
                            g.setFont(number);
                            g.drawString("2", (xi / 8) * (2 * x + 1) - xi / 48, (yi / 8) * (2 * y + 1) + yi / 48);
                            break;
                        case 2:
                            g.setColor(new Color(237, 226, 184));
                            g.fillRect(1 + x * xi / 4, 1 + y * yi / 4, (xi / 4) - 2, (yi / 4) - 2);
                            g.setColor(Color.black);
                            g.setFont(number);
                            g.drawString("4", (xi / 8) * (2 * x + 1) - xi / 48, (yi / 8) * (2 * y + 1) + yi / 48);
                            break;
                        case 3:
                            g.setColor(new Color(242, 194, 121));
                            g.fillRect(1 + x * xi / 4, 1 + y * yi / 4, (xi / 4) - 2, (yi / 4) - 2);
                            g.setColor(Color.black);
                            g.setFont(number);
                            g.drawString("8", (xi / 8) * (2 * x + 1) - xi / 48, (yi / 8) * (2 * y + 1) + yi / 48);
                            break;
                        case 4:
                            g.setColor(new Color(245, 159, 99));
                            g.fillRect(1 + x * xi / 4, 1 + y * yi / 4, (xi / 4) - 2, (yi / 4) - 2);
                            g.setColor(Color.black);
                            g.setFont(number);
                            g.drawString("16", (xi / 8) * (2 * x + 1) - xi / 32, (yi / 8) * (2 * y + 1) + yi / 48);
                            break;

                        case 5:
                            g.setColor(new Color(246, 124, 95));
                            g.fillRect(1 + x * xi / 4, 1 + y * yi / 4, (xi / 4) - 2, (yi / 4) - 2);
                            g.setColor(Color.black);
                            g.setFont(number);
                            g.drawString("32", (xi / 8) * (2 * x + 1) - xi / 32, (yi / 8) * (2 * y + 1) + yi / 48);
                            break;
                        case 6:
                            g.setColor(new Color(246, 86, 59));
                            g.fillRect(1 + x * xi / 4, 1 + y * yi / 4, (xi / 4) - 2, (yi / 4) - 2);
                            g.setColor(Color.black);
                            g.setFont(number);
                            g.drawString("64", (xi / 8) * (2 * x + 1) - xi / 32, (yi / 8) * (2 * y + 1) + yi / 48);
                            break;
                        case 7:
                            g.setColor(new Color(237, 207, 114));
                            g.fillRect(1 + x * xi / 4, 1 + y * yi / 4, (xi / 4) - 2, (yi / 4) - 2);
                            g.setColor(Color.black);
                            g.setFont(number);
                            g.drawString("128", (xi / 8) * (2 * x + 1) - xi / 24, (yi / 8) * (2 * y + 1) + yi / 48);
                            break;

                        case 8:
                            g.setColor(new Color(237, 214, 97));
                            g.fillRect(1 + x * xi / 4, 1 + y * yi / 4, (xi / 4) - 2, (yi / 4) - 2);
                            g.setColor(Color.black);
                            g.setFont(number);
                            g.drawString("256", (xi / 8) * (2 * x + 1) - xi / 24, (yi / 8) * (2 * y + 1) + yi / 48);
                            break;
                        case 9:
                            g.setColor(new Color(237, 218, 80));
                            g.fillRect(1 + x * xi / 4, 1 + y * yi / 4, (xi / 4) - 2, (yi / 4) - 2);
                            g.setColor(Color.black);
                            g.setFont(number);
                            g.drawString("512", (xi / 8) * (2 * x + 1) - xi / 24, (yi / 8) * (2 * y + 1) + yi / 48);
                            break;
                        case 10:
                            g.setColor(new Color(237, 255, 63));
                            g.fillRect(1 + x * xi / 4, 1 + y * yi / 4, (xi / 4) - 2, (yi / 4) - 2);
                            g.setColor(Color.black);
                            g.setFont(number);
                            g.drawString("1024", (xi / 8) * (2 * x + 1) - xi / 16, (yi / 8) * (2 * y + 1) + yi / 48);
                            break;
                        case 11:
                            g.setColor(new Color(90, 227, 113));
                            g.fillRect(1 + x * xi / 4, 1 + y * yi / 4, (xi / 4) - 2, (yi / 4) - 2);
                            g.setColor(Color.black);
                            g.setFont(number);
                            g.drawString("2048", (xi / 8) * (2 * x + 1) - xi / 14, (yi / 8) * (2 * y + 1) + yi / 48);
                            break;
                        case 0:
                            break;
                    }
                }
            }
            try {
                refreshHighScore();
                refreshLabel(Score, tileMoves, hs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println(hs);
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    Up();
                    addNumber();
                    dontAdd = false;
                    printer();
                    repaint();
                    break;

                case KeyEvent.VK_S:
                    Down();
                    addNumber();
                    dontAdd = false;
                    printer();
                    repaint();
                    break;

                case KeyEvent.VK_A:
                    Left();
                    addNumber();
                    dontAdd = false;
                    printer();
                    repaint();
                    break;

                case KeyEvent.VK_D:
                    Right();
                    addNumber();
                    dontAdd = false;
                    printer();
                    repaint();
                    break;

                default:
                    break;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    public void refreshHighScore() throws IOException {
        File hsf = new File("hs.txt");
        FileReader fr = new FileReader(hsf);
        BufferedReader br = new BufferedReader(fr);
        String s;
        while ((s = br.readLine()) != null) {
            hs = Integer.parseInt(s);
        }
    }

    public void refreshLabel(int Scr, int mvs, int highs) throws IOException {

        score.setText("Score : " + Scr);
        moves.setText("Moves : " + (mvs - 2));
        highScore.setText("High Score :" + highs);
        if (win()) {
            score.setText("Score : " + Scr);
            state.setText("You win!");
        } else if (isFull() && !canMove()) {
            score.setText("Score : " + Scr);
            state.setText("You lose!");
        }
        if(Scr > highs){
            writer(Scr);
        }
    }

    public void writer(int h) throws IOException {
        File hsf = new File("hs.txt");
        FileWriter fw = new FileWriter(hsf);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.write(h + "");
        pw.flush();
        pw.close();
        bw.close();
        fw.close();
    }
}
