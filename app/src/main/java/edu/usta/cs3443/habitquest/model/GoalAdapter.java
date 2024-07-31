package edu.usta.cs3443.habitquest.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import edu.usta.cs3443.habitquest.R;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {
    private List<Goal> goals;
    private Context context;

    public GoalAdapter(List<Goal> goals , Context context) {
        this.goals = goals;
        this.context = context; // Added context to the constructor to be able to save the state to CSV.
    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goal, parent, false);
        return new GoalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        Goal goal = goals.get(position);

        // Set goal details
        holder.goalName.setText(goal.getGoalName());
        holder.goalType.setText(goal.getGoalType());
        holder.goalDescription.setText(goal.getGoalDescription());
        holder.goalStart.setText(goal.getGoalStart());
        holder.goalEnd.setText(goal.getGoalEnd());

        // Set the CheckBox state based on goalCompleted
        holder.goalCompleted.setChecked(goal.isGoalCompleted());

        // Set a listener to handle changes in the CheckBox
        holder.goalCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            goal.setGoalCompleted(isChecked);
            // Save the state to the CSV file
            try {
                Goal.updateGoalInCSV(goal, context); // Added call to update CSV when checkbox is changed
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        holder.deleteGoal.setOnClickListener(v -> {
            // Handle delete action
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Goal goalToDelete = goals.get(adapterPosition);
                deleteGoal(goalToDelete, holder.itemView.getContext(), adapterPosition);
            }
        });
    }

    private void deleteGoal(Goal goal, Context context, int position) {
        if (position >= 0 && position < goals.size()) {
            if (goals.contains(goal)) {
                goals.remove(position);
                notifyItemRemoved(position);
                // Delete goal from file
                try {
                    Log.d("GoalAdapter", "Attempting to delete goal: " + goal.getGoalName());
                    Goal.deleteGoalFromCSV(goal, context);
                    Log.d("GoalAdapter", "Goal deleted successfully");
                } catch (IOException e) {
                    Log.e("GoalAdapter", "Error deleting goal from CSV", e);
                }
            } else {
                Log.d("GoalAdapter", "Goal not found in list: " + goal.getGoalName());
            }
        } else {
            Log.e("GoalAdapter", "Invalid position: " + position);
        }
    }



    @Override
    public int getItemCount() {
        return goals.size();
    }

    public static class GoalViewHolder extends RecyclerView.ViewHolder {
        TextView goalName;
        TextView goalType;
        TextView goalDescription;
        TextView goalStart;
        TextView goalEnd;
        CheckBox goalCompleted;
        TextView deleteGoal;

        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            goalName = itemView.findViewById(R.id.goalName);
            goalType = itemView.findViewById(R.id.goalType);
            goalDescription = itemView.findViewById(R.id.goalDescription);
            goalStart = itemView.findViewById(R.id.goalStart);
            goalEnd = itemView.findViewById(R.id.goalEnd);
            goalCompleted = itemView.findViewById(R.id.goalCompleted);
            deleteGoal = itemView.findViewById(R.id.deleteGoal);
        }
    }
}