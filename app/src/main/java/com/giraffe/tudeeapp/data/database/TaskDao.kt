import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.giraffe.tudeeapp.data.Task
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

@Dao
interface TaskDao {

    @Query("SELECT * FROM task WHERE dueDate = :date")
    fun getTasksByDate(date: LocalDateTime): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE categoryId = :categoryId")
    fun getTasksByCategory(categoryId: Long): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE uid = :id")
    suspend fun getTaskById(id: Long): Task

    @Insert
    suspend fun createTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task): Long

    @Delete
    suspend fun deleteTask(id: Long): Long

    @Query("UPDATE task SET status = :newStatus WHERE uid = :id")
    suspend fun changeStatus(id: Long, newStatus: TaskStatus): Unit
}