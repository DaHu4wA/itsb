package at.ac.fhsalzburg.mmtlb.gui.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import at.ac.fhsalzburg.mmtlb.Stoppable;

/**
 * Glass Pane to block the GUI while working
 * 
 * @author Stefan Huber
 */
public class MmtGlassPane extends JPanel {
	private static final long serialVersionUID = 1L;

	private final ImageIcon workingIcon;
	private Stoppable stoppable = null;

	public MmtGlassPane() {

		workingIcon = new ImageIcon(MmtGlassPane.class.getResource("working.gif"));

		setOpaque(false);
		setBackground(new Color(100, 100, 100, 145));

		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
					if (stoppable != null) {
						stoppable.stopTask();
					}
					return;
				}
				e.consume();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				e.consume();
			}
		});
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				e.consume();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				e.consume();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				e.consume();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				e.consume();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				e.consume();
			}
		});
		setFocusTraversalKeysEnabled(false);
	}

	@Override
	public void setVisible(boolean visible) {

		if (visible) {
			setBackground(new Color(100, 100, 100, 0));
			super.setVisible(visible);
			requestFocusInWindow();

			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					int x = 0;
					while (x < 146) {
						final int alpha = x;
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								setBackground(new Color(100, 100, 100, alpha));
							}
						});
						x += 5;
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
						}
					}
				}
			});
			t.start();

		} else {
			super.setVisible(visible);
			stoppable = null;
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		int w = getSize().width;
		int h = getSize().height;
		g.setColor(getBackground());

		g.fillRect(0, 0, w, h - 30);

		if (getBackground().getAlpha() > 10) {
			workingIcon.paintIcon(this, g, w / 2 - workingIcon.getIconWidth() / 2, h / 2 - workingIcon.getIconHeight() / 2);
		}

		if (getBackground().getAlpha() > 20) {
			g.setColor(Color.white);

			g.setFont(new Font("Arial", Font.BOLD, 18));
			g.drawString("Press ESC to abort", w / 2 - 78, h / 2 + workingIcon.getIconHeight() / 2 + 35);

			g.setFont(new Font("Arial", Font.BOLD, 18));
			g.drawString("APPLYING", w / 2 - 45, h / 2 - 5);
			g.drawString("FILTER", w / 2 - 30, h / 2 + 18);
		}

	}

	public void setStoppable(Stoppable stoppable) {
		this.stoppable = stoppable;
	}
}
