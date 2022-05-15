
<link rel="stylesheet" href="../css/ForumStyle.css" />

<header>

<?php
        echo '<div class="container">';
            echo '<nav>';
                echo '<ul>';
                   echo '<li><a href="index.php">Home</a></li>';
                   echo '<li><a href="forum.php">Forum</a></li>';
                   echo '<li><a href="basic_care.php">Basic Care</a></li>';
                   echo '<li><a href="diet.php">Diet</a></li>';
                   echo '<li><a href="shells.php">Shells</a></li>';
                   echo '<li>';
                   
                        session_start();

                        if(isset($_SESSION['signed_in']))
                        {
                            echo  $_SESSION['user_name'] . ' Not you? <a href="signout.php">Sign out</a>';
                        }
                        else
                        {
                            echo '<script>
                            function openForm() {
                                document.getElementById("myForm").style.display = "block";
                            }
                    
                            function closeForm() {
                                document.getElementById("myForm").style.display = "none";
                            }
                            </script>';
                            
                            echo '<li><a href="signin.php">Sign in</a></li>';
                            echo '<li><a href="signup.php">Sign up</a></li>';
                        }

                    echo '</li>';
                echo '</ul>';
            echo '</nav>';
        echo '</div>';

        
?>
</header>
