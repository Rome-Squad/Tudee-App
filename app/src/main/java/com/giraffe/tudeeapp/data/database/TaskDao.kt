import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.giraffe.tudeeapp.data.dto.TaskDto
import com.giraffe.tudeeapp.data.util.DatabaseConstants.TASK_TABLE_NAME
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM $TASK_TABLE_NAME WHERE dueDate = :date")
    fun getTasksByDate(date: String): Flow<List<TaskDto>>

    @Query("SELECT * FROM $TASK_TABLE_NAME WHERE categoryId = :categoryId")
    fun getTasksByCategory(categoryId: Long): Flow<List<TaskDto>>

    @Query("SELECT * FROM $TASK_TABLE_NAME WHERE uid = :id")
    suspend fun getTaskById(id: Long): TaskDto

    @Insert
    suspend fun createTask(taskDto: TaskDto): Long

    @Update
    suspend fun updateTask(taskDto: TaskDto)

    @Query("DELETE FROM $TASK_TABLE_NAME WHERE uid = :id")
    suspend fun deleteTask(id: Long)

    @Query("UPDATE $TASK_TABLE_NAME SET status = :newStatus WHERE uid = :id")
    suspend fun changeStatus(id: Long, newStatus: TaskStatus)
}