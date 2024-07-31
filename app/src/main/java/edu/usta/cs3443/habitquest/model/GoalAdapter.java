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
/**
 *
 * @author Katarah (Kat) Griffin,Alistair Chambers, Muskan Devi,Christian (Ian) Fernandez
 *
 * Cs 3443 Summer 2024 - Group Project
 */
public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {
    private final List<Goal> goals;
    private final Context context;

    /**
     * Constructor for GoalAdapter.
     * @param goals List of goals to display.
     * @param context Context of the activity or fragment.
     */
    public GoalAdapter(List<Goal> goals , Context context) {
        this.goals = goals;
        this.context = context; // Added context to the constructor to be able to save the state to CSV.
    }

    /**
     * Called when RecyclerView needs a new {@link GoalViewHolder} of the given type to represent
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goal, parent, false);
        return new GoalViewHolder(view);
    }

    /**
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
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

    /**
     * Deletes a goal from the list and updates the RecyclerView.
     * @param goal The goal to delete.
     * @param context The context of the activity or fragment.
     * @param position The position of the goal in the list.
     */
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


    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return goals.size();
    }

    /**
     * ViewHolder class for representing a goal in the RecyclerView.
     */
    public static class GoalViewHolder extends RecyclerView.ViewHolder {
        TextView goalName;
        TextView goalType;
        TextView goalDescription;
        TextView goalStart;
        TextView goalEnd;
        CheckBox goalCompleted;
        TextView deleteGoal;

        /**
         * Constructor for GoalViewHolder.
         * @param itemView The View that represents the item in the RecyclerView.
         */
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