package com.todotask.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.todotask.R;
import com.todotask.data.model.Todo;
import com.todotask.databinding.ListItemTodoBinding;
import com.todotask.ui.callback.ITodoItemListener;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {
    @Nullable
    private final ITodoItemListener mCallback;
    private List<? extends Todo> mTodoList;
    private Context mContext;

    public TodoListAdapter(@Nullable ITodoItemListener callBack) {
        mCallback = callBack;
    }

    public void setTodoList(final List<? extends Todo> todoList) {
        if (mTodoList == null) {
            mTodoList = todoList;
            notifyItemRangeInserted(0, mTodoList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mTodoList.size();
                }

                @Override
                public int getNewListSize() {
                    return todoList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mTodoList.get(oldItemPosition).getId() == todoList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Todo newTodo = todoList.get(newItemPosition);
                    Todo oldTodo = mTodoList.get(oldItemPosition);
                    return newTodo.getId() == oldTodo.getId()
                            && newTodo.getTitle().equals(oldTodo.getTitle())
                            && newTodo.getDescription().equals(oldTodo.getDescription());
                }
            });
            mTodoList = todoList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        ListItemTodoBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(mContext), R.layout.list_item_todo,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mBinding.setTodo(mTodoList.get(position));
        holder.mBinding.setCallback(mCallback);
        holder.mBinding.menu.setOnClickListener((View v) -> {
            Todo todo = (Todo) v.getTag();
            PopupMenu popup = new PopupMenu(mContext, v);
            popup.setOnMenuItemClickListener((MenuItem item) -> {
                if (mCallback != null)
                    mCallback.onMenuClicked(item.getItemId(), todo);
                return true;
            });
            popup.inflate(R.menu.list_item_menu);
            popup.show();
        });
    }

    @Override
    public int getItemCount() {
        return mTodoList != null ? mTodoList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ListItemTodoBinding mBinding;

        ViewHolder(ListItemTodoBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}
