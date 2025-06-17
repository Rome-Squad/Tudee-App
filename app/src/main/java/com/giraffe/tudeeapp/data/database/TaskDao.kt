import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.giraffe.tudeeapp.data.model.TaskEntity
import com.giraffe.tudeeapp.data.util.Constants.TASK_TABLE_NAME
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM $TASK_TABLE_NAME WHERE dueDate = :date")
    fun getTasksByDate(date: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM $TASK_TABLE_NAME WHERE categoryId = :categoryId")
    fun getTasksByCategory(categoryId: Long): Flow<List<TaskEntity>>

    @Query("SELECT * FROM $TASK_TABLE_NAME WHERE uid = :id")
    suspend fun getTaskById(id: Long): TaskEntity

    @Insert
    suspend fun createTask(taskEntity: TaskEntity): Long

    @Update
    suspend fun updateTask(taskEntity: TaskEntity)

    @Query("DELETE FROM $TASK_TABLE_NAME WHERE uid = :id")
    suspend fun deleteTask(id: Long)

    @Query("UPDATE $TASK_TABLE_NAME SET status = :newStatus WHERE uid = :id")
    suspend fun changeStatus(id: Long, newStatus: TaskStatus)
}