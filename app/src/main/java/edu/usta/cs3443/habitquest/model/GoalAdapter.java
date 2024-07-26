package edu.usta.cs3443.habitquest.model;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.usta.cs3443.habitquest.R;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {
    private List<Goal> goals;

    public GoalAdapter(List<Goal> goals) {
        this.goals = goals;
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
            // Notify the model or database of the change
            // You might need to update the data source or notify the database here
        });
        // Set a listener to handle edit and delete actions
        holder.editGoal.setOnClickListener(v -> {
            // Handle edit action
            // Open a dialog or activity to edit the goal

        });
        holder.deleteGoal.setOnClickListener(v -> {
            // Handle delete action
            // Remove the goal from the list and notify the adapter

            //check if the goal is within the list
            if (goals.contains(goal)) {
                goals.remove(goal);
               // goals.remove(position);
                notifyItemRemoved(position);

            }else{
                //goal is not in the list
                Log.d("GoalAdapter", "Goal not found in list");
                //show all the goals to console
                Log.d("GoalAdapter", "Goals:");
                for (Goal g : goals) {
                    Log.d("GoalAdapter", g.getGoalName());
                }
                //name of current goal
                String goalName = goal.getGoalName();
                Log.d("GoalAdapter", "Goal not found in list: " + goalName);

            }
        });
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
        TextView editGoal;
        TextView deleteGoal;

        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            goalName = itemView.findViewById(R.id.goalName);
            goalType = itemView.findViewById(R.id.goalType);
            goalDescription = itemView.findViewById(R.id.goalDescription);
            goalStart = itemView.findViewById(R.id.goalStart);
            goalEnd = itemView.findViewById(R.id.goalEnd);
            goalCompleted = itemView.findViewById(R.id.goalCompleted);
            editGoal = itemView.findViewById(R.id.editGoal);
            deleteGoal = itemView.findViewById(R.id.deleteGoal);
        }
    }
}