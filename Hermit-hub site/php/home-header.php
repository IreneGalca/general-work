
<link rel="stylesheet" href="../css/HomeStyle.css" />

<header>

<?php
        echo '<div class="container">';
            echo '<nav>';
                echo '<ul>';
                   echo '<li><a href="index.php">Home</a></li>';
                   echo '<li><a href="forum.php">Forum</a></li>';
                   echo '<li><a href="Basic_Care.html">Basic Care</a></li>';
                   echo '<li><a href="#">Diet</a></li>';
                   echo '<li><a href="#">Shells</a></li>';
                   echo '<li>';
                   
                        session_start();

                        if(isset($_SESSION['signed_in']))
                        {
                            echo 'Hello ' . $_SESSION['user_name'] . '! Not you? <a href="signout.php">Sign out</a>';
                        }
                        else
                        {
                            echo '<li><a href="signin.php">Sign in</a></li>';
                            echo '<li><a href="signup.php">Sign up</a></li>';
                        }

                    echo '</li>';
                echo '</ul>';
            echo '</nav>';
        echo '</div>';

        
?>
</header>
