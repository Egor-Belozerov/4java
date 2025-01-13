import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main extends JPanel {
    private int ballX = 100;
    private int ballY = 100;
    private int ballDiameter = 100;
    private int deltaX = 10;
    private int deltaY = 10;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Движение окружности");
        Main app = new Main();
        frame.add(app);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Timer timer = new Timer(30, e -> app.moveBall());
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Рисуем окружность
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);
        g2d.fillOval(ballX, ballY, ballDiameter, ballDiameter);
    }

    private void moveBall() {
        ballX += deltaX;
        ballY += deltaY;

        // Проверяем столкновения с границами
        if (ballX <= 0 || ballX + ballDiameter >= getWidth()) {
            deltaX = -deltaX;
            compressBall();
        }
        if (ballY <= 0 || ballY + ballDiameter >= getHeight()) {
            deltaY = -deltaY;
            compressBall();
        }

        // Обновляем экран после изменения
        repaint();
    }

    private void compressBall() {
        int originalDiameter = ballDiameter;
        ballDiameter -= 10; // Уменьшаем диаметр
        repaint(); // Обновляем экран для визуализации изменений

        // Восстанавливаем диаметр через 100 мс
        Timer compressTimer = new Timer(100, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ballDiameter = originalDiameter;
                ((Timer) e.getSource()).stop();
                repaint();
            }
        });
        compressTimer.setRepeats(false); // Таймер срабатывает один раз
        compressTimer.start();
    }
}
