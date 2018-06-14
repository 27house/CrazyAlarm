package com.sunn.xhui.crazyalarm.ui.garden;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.mpv.contract.LoginContract;
import com.sunn.xhui.crazyalarm.mpv.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginContract.View, Toolbar.OnMenuItemClickListener {

	/**
	 * Id to identity READ_CONTACTS permission request.
	 */
	private static final int REQUEST_READ_CONTACTS = 0;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.email)
	AutoCompleteTextView mEmailView;
	@BindView(R.id.password)
	EditText mPasswordView;
	@BindView(R.id.email_sign_in_button)
	Button emailSignInButton;

	private LoginPresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		toolbar.setTitle(R.string.login_title);
		toolbar.setOnMenuItemClickListener(this);
		mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
				if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
					attemptLogin();
					return true;
				}
				return false;
			}
		});

		presenter = new LoginPresenter(this);
	}

	@OnClick(R.id.email_sign_in_button)
	public void clickLogin(View view) {
		attemptLogin();
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	private void attemptLogin() {
		if (presenter == null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		String email = mEmailView.getText().toString();
		String password = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password, if the user entered one.
		if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		}/* else if (!isEmailValid(email)) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}*/

		if (cancel) {
			focusView.requestFocus();
		} else {
			try {
				presenter.login(email, password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isEmailValid(String email) {
		//TODO: Replace this with your own logic
		return email.contains("@");
	}

	private boolean isPasswordValid(String password) {
		//TODO: Replace this with your own logic
		return password.length() > 4;
	}

	@Override
	public void loginResult(boolean success, String tip) {
		Snackbar.make(emailSignInButton, tip, Snackbar.LENGTH_SHORT).show();
		if (success) {
			finish();
		} else {
			mPasswordView.setError(getString(R.string.error_incorrect_password));
			mPasswordView.requestFocus();
		}
	}

	@Override
	public void registerResult(boolean success, String tip) {
		Snackbar.make(emailSignInButton, tip, Snackbar.LENGTH_SHORT).show();
		if (success) {
			String email = mEmailView.getText().toString();
			String password = mPasswordView.getText().toString();
			try {
				presenter.login(email, password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			mPasswordView.setError(getString(R.string.error_incorrect_password));
			mPasswordView.requestFocus();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.clear();
		getMenuInflater().inflate(R.menu.menu_login, menu);
		return true;
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_register:
				// 注册
				// Store values at the time of the login attempt.
				String email = mEmailView.getText().toString();
				String password = mPasswordView.getText().toString();
				presenter.register(email, password);
				break;
			default:
				break;
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.unSubscribe();
	}
}

