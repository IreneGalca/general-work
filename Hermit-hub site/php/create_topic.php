<link rel="stylesheet" href="../css/TopicStyle.css" />

<?php
    include 'connect.php';
    include 'header.php';

    if(isset($_SESSION['signed_in'])==false)
    {
        echo 'Sorry, you have to be <a href="signin.php">signed in</a> to create a topic.';
    }
    else{
            echo '<form>
            <label>Title:</label> <br>
            <input type="text" name="post_topic" /><br>
            <textarea name="post_content"/></textarea><br>
            <input type="submit" class="btn" value="Post"  />
            </form>';

           

            $sql = "INSERT INTO
                    posts(post_topic, post_content, post_date, post_by)
                    VALUES('" . mysqli_real_escape_string($conn, empty($_POST['post_topic'])) . "',
                        " . mysqli_real_escape_string($conn, empty($_POST['post_content'])) . ",
                        NOW(),
                        " . $_SESSION['user_id'] . "
                   )";

            $result = mysqli_query($conn, $sql);
            
        if(!$result){
           echo "alert('something went wrong with your post. Try again?')";
        }
        else
        {
            echo 'Ya did it. Wanna <a href="forum.php"> see it </a>';
        }
    }    


    include 'footer.php';

?>