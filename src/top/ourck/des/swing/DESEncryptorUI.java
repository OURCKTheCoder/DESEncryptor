package top.ourck.des.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ListModel;

import top.ourck.des.advance.MultiDESEncryptor;
import top.ourck.des.frontend.DESDecryptor;
import top.ourck.des.frontend.DESEncryptor;
import top.ourck.utils.TypeConvert;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractListModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import static top.ourck.utils.TypeConvert.*;

public class DESEncryptorUI {

	private JFrame frame;
	private JTextField mono_KeyBinStr;
	private JTextField mono_CipherBinStr;
	private JTextField multi_MessageBinStr;
	private JTextField multi_CipherBinStr;
	private JTextField multi_DESencTimesStr;
	private JTextField mono_MessageBinStr;
	private JTextField mono_MessageStr;
	private JTextField mono_CipherStr;
	private JTextField multi_MessageStr;
	private JTextField multi_CipherStr;
	private JList<String> multi_KeysBinStrs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DESEncryptorUI window = new DESEncryptorUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DESEncryptorUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 825, 531);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 789, 448);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Single DES Encrypting", null, panel, null);
		panel.setLayout(null);
		
		mono_MessageBinStr = new JTextField();
		mono_MessageBinStr.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String bitsStr = mono_MessageBinStr.getText();
				byte[] bits = parseStr2Bits(bitsStr);
				mono_MessageStr.setText(bits2Str(bits));
			}
		});
		mono_MessageBinStr.setText("0000000100100011010001010110011110001001101010111100110111101101");
		mono_MessageBinStr.setBounds(74, 10, 474, 21);
		panel.add(mono_MessageBinStr);
		mono_MessageBinStr.setColumns(10);
		
		JLabel lblMessage = new JLabel("Message");
		lblMessage.setBounds(10, 13, 54, 15);
		panel.add(lblMessage);
		
		JLabel lblKey = new JLabel("Key");
		lblKey.setBounds(10, 41, 54, 15);
		panel.add(lblKey);
		
		mono_KeyBinStr = new JTextField();
		mono_KeyBinStr.setText("0001001100110100010101110111100110011011101111001101111111110001");
		mono_KeyBinStr.setBounds(74, 38, 474, 21);
		panel.add(mono_KeyBinStr);
		mono_KeyBinStr.setColumns(10);
		
		mono_CipherBinStr = new JTextField();
		mono_CipherBinStr.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String bitsStr = mono_CipherBinStr.getText();
				byte[] bits = parseStr2Bits(bitsStr);
				mono_CipherStr.setText(bits2Str(bits));
			}
		});
		mono_CipherBinStr.setBounds(74, 66, 474, 21);
		panel.add(mono_CipherBinStr);
		mono_CipherBinStr.setColumns(10);
		
		JLabel lblCipher = new JLabel("Cipher");
		lblCipher.setBounds(10, 69, 54, 15);
		panel.add(lblCipher);
		
		JButton btnExcrypt = new JButton("Encrypt");
		btnExcrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte[] mbs = TypeConvert.parseStr2Bits(mono_MessageBinStr.getText());
				byte[] kbs = TypeConvert.parseStr2Bits(mono_KeyBinStr.getText());
				
				try {
					byte[] result = new DESEncryptor().encrypt(mbs, kbs);
					StringBuilder stb = new StringBuilder();
					for(byte b : result) stb.append(String.valueOf(b));
					mono_CipherBinStr.setText(stb.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnExcrypt.setBounds(126, 97, 93, 23);
		panel.add(btnExcrypt);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte[] mbs = TypeConvert.parseStr2Bits(mono_CipherBinStr.getText());
				byte[] kbs = TypeConvert.parseStr2Bits(mono_KeyBinStr.getText());
				
				try {
					byte[] result = new DESDecryptor().decrypt(mbs, kbs);
					StringBuilder stb = new StringBuilder();
					for(byte b : result) stb.append(String.valueOf(b));
					mono_MessageBinStr.setText(stb.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnDecrypt.setBounds(564, 97, 93, 23);
		panel.add(btnDecrypt);
		
		JButton btnClear_1 = new JButton("Clear");
		btnClear_1.setBounds(345, 97, 93, 23);
		panel.add(btnClear_1);
		
		mono_MessageStr = new JTextField();
		mono_MessageStr.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String str = mono_MessageStr.getText();
				mono_MessageBinStr.setText(parseBits2Str(bytes2Bits(str2Bytes(str))));
			}
		});
		mono_MessageStr.setColumns(10);
		mono_MessageStr.setBounds(558, 10, 217, 21);
		panel.add(mono_MessageStr);
		
		mono_CipherStr = new JTextField();
		mono_CipherStr.setColumns(10);
		mono_CipherStr.setBounds(558, 66, 217, 21);
		panel.add(mono_CipherStr);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		tabbedPane.addTab("Multi-DES Encrypting", null, panel_1, null);
		
		multi_MessageBinStr = new JTextField();
		multi_MessageBinStr.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String bitsStr = multi_MessageBinStr.getText();
				byte[] bits = parseStr2Bits(bitsStr);
				multi_MessageStr.setText(bits2Str(bits));
			}
		});
		multi_MessageBinStr.setText("0000000100100011010001010110011110001001101010111100110111101101");
		multi_MessageBinStr.setColumns(10);
		multi_MessageBinStr.setBounds(74, 10, 471, 21);
		panel_1.add(multi_MessageBinStr);
		
		JLabel label = new JLabel("Message");
		label.setBounds(10, 13, 54, 15);
		panel_1.add(label);
		
		JLabel lblKeys = new JLabel("Keys");
		lblKeys.setBounds(10, 41, 54, 15);
		panel_1.add(lblKeys);
		
		multi_CipherBinStr = new JTextField();
		multi_CipherBinStr.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String bitsStr = multi_CipherBinStr.getText();
				byte[] bits = parseStr2Bits(bitsStr);
				multi_CipherStr.setText(bits2Str(bits));
			}
		});
		multi_CipherBinStr.setColumns(10);
		multi_CipherBinStr.setBounds(74, 355, 471, 21);
		panel_1.add(multi_CipherBinStr);
		
		JLabel label_2 = new JLabel("Cipher");
		label_2.setBounds(10, 358, 54, 15);
		panel_1.add(label_2);
		
		// TODO Consider using TypeConvert utilities!
		JButton button = new JButton("Encrypt");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] mcs = multi_MessageBinStr.getText().toCharArray();
				byte[] mbs = new byte[mcs.length];
				for(int i = 0; i < mbs.length; i++) {
					mbs[i] = (byte)(mcs[i] - '0'); // Unsafe!
				}
				
				ListModel<String> keys = multi_KeysBinStrs.getModel();
				int nKeys = keys.getSize();
				byte[][] kbs = new byte[nKeys][];
				for(int i = 0; i < nKeys; i++) {
					String keyStr = keys.getElementAt(i);
					char[] kcs = keyStr.toCharArray();
					kbs[i] = new byte[kcs.length];
					for(int j = 0; j < kbs.length; j++) {
						kbs[i][j] = (byte)(kcs[j] - '0'); // Unsafe!
					}
				}
				
				int encTimes = Integer.parseInt(multi_DESencTimesStr.getText());
				try {
					byte[] result = new MultiDESEncryptor(encTimes).encrypt(mbs, kbs);
					StringBuilder stb = new StringBuilder();
					for(byte b : result) stb.append(String.valueOf(b));
					multi_CipherBinStr.setText(stb.toString());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		button.setBounds(126, 386, 93, 23);
		panel_1.add(button);
		
		// TODO Consider using TypeConvert utilities!
		JButton button_1 = new JButton("Decrypt");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				char[] mcs = multi_CipherBinStr.getText().toCharArray();
				byte[] mbs = new byte[mcs.length];
				for(int i = 0; i < mbs.length; i++) {
					mbs[i] = (byte)(mcs[i] - '0'); // Unsafe!
				}
				
				ListModel<String> keys = multi_KeysBinStrs.getModel();
				int nKeys = keys.getSize();
				byte[][] kbs = new byte[nKeys][];
				for(int i = 0; i < nKeys; i++) {
					String keyStr = keys.getElementAt(i);
					char[] kcs = keyStr.toCharArray();
					kbs[i] = new byte[kcs.length];
					for(int j = 0; j < kbs.length; j++) {
						kbs[i][j] = (byte)(kcs[j] - '0'); // Unsafe!
					}
				}
				
				int encTimes = Integer.parseInt(multi_DESencTimesStr.getText());
				try {
					byte[] result = new MultiDESEncryptor(encTimes).decrypt(mbs, kbs);
					StringBuilder stb = new StringBuilder();
					for(byte b : result) stb.append(String.valueOf(b));
					multi_MessageBinStr.setText(stb.toString());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		button_1.setBounds(564, 386, 93, 23);
		panel_1.add(button_1);
		
		multi_KeysBinStrs = new JList<>();
		multi_KeysBinStrs.setModel(new AbstractListModel<String>() {
			String[] values = new String[] {"0001001100110100010101110111100110011011101111001101111111110001", "0000000000000000000000000000000000000000000000000000000000000001", "1000000000000000000000000000000000000000000000000000000000000000"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		multi_KeysBinStrs.setBounds(74, 41, 471, 304);
		panel_1.add(multi_KeysBinStrs);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(345, 386, 93, 23);
		panel_1.add(btnClear);
		
		multi_DESencTimesStr = new JTextField();
		multi_DESencTimesStr.setText("3");
		multi_DESencTimesStr.setBounds(683, 386, 25, 23);
		panel_1.add(multi_DESencTimesStr);
		multi_DESencTimesStr.setColumns(10);
		
		JLabel lblTimes = new JLabel("Times");
		lblTimes.setBounds(718, 390, 54, 15);
		panel_1.add(lblTimes);
		
		multi_MessageStr = new JTextField();
		multi_MessageStr.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String str = multi_MessageStr.getText();
				multi_MessageBinStr.setText(parseBits2Str(bytes2Bits(str2Bytes(str))));
			}
		});
		multi_MessageStr.setColumns(10);
		multi_MessageStr.setBounds(555, 10, 217, 21);
		panel_1.add(multi_MessageStr);
		
		multi_CipherStr = new JTextField();
		multi_CipherStr.setColumns(10);
		multi_CipherStr.setBounds(555, 355, 217, 21);
		panel_1.add(multi_CipherStr);
		
	}
}
