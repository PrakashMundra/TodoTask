package com.todotask.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.todotask.R;
import com.todotask.data.model.Todo;

public class IntentUtils {
    public static void shareTodo(Context context, Todo todo) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, todo.getTitle());
        shareIntent.putExtra(Intent.EXTRA_TEXT, todo.getDescription());
        if (shareIntent.resolveActivity(context.getPackageManager()) != null)
            context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share)));
        else
            Toast.makeText(context, "Action not found", Toast.LENGTH_SHORT).show();
    }
}
