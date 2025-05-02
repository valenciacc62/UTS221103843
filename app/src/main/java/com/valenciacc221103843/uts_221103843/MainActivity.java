package com.valenciacc221103843.uts_221103843;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {

    EditText urlEditText;
    Button showButton;
    WebView webView;
    String NIM = "221103843";
    String NAMA = "Valencia Calvina Chaterin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlEditText = findViewById(R.id.urlEditText);
        showButton = findViewById(R.id.showButton);
        webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        createNotificationChannel();

        showButton.setOnClickListener(v -> {
            String url = urlEditText.getText().toString();
            if (!url.startsWith("http")) {
                url = "https://" + url;
            }

            webView.loadUrl(url);

            String message = "NIM: " + NIM + ", Nama: " + NAMA + "\nURL: " + url;
            showNotification("Informasi URL", message);
        });
    }

    private void showNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "webview_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "WebViewChannel";
            String description = "Channel untuk notifikasi WebView";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("webview_channel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
