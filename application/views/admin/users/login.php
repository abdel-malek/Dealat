
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Dealat Control panel</title>

    <!-- Bootstrap -->
    <link href="<?php echo base_url();?>admin_assets/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- <link href="<?php echo base_url() ?>admin_assets/css/rtl.css" rel="stylesheet"> -->
    <!-- Font Awesome -->
    <!-- <link href="<?php echo base_url();?>Files/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet"> -->
    <link href="https://fonts.googleapis.com/css?family=Cairo" rel="stylesheet">
    <!-- Animate.css -->
    <link href="https://colorlib.com/polygon/gentelella/css/animate.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="<?php echo base_url();?>admin_assets/build/css/custom.min.css" rel="stylesheet">
  </head>

  <body class="login">
    <div>
     <?php if($this->session->flashdata('error')): ?>
 	     <div class="alert alert-danger alert-dismissible fade in" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span>
            </button>
            <strong></strong><?php echo $this->session->flashdata('error')?>
          </div>
     <?php endif; ?>
     <?php if(isset($error)&& $error != ''): ?>
 	     <div class="alert alert-danger alert-dismissible fade in" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span>
            </button>
            <strong>Sorry! </strong><?php echo $error?>
          </div>
     <?php endif; ?>
      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
            <form  method="post" action="<?php echo base_url();?>index.php/admin/users_manage/login">
              <h1><?php echo $this->lang->line('login_form') ?></h1>
              <div>
                <input type="text"  name="admin_username" class="form-control" placeholder="<?php echo $this->lang->line('username') ?>"/>
              </div>
              <div>
                <input type="password" name="admin_password" class="form-control" placeholder="<?php echo $this->lang->line('password') ?>" />
              </div>
              <div>
                <button class="btn btn-default submit"  type="submit"><?php echo $this->lang->line('login') ?></button>
              </div>

              <div class="clearfix"></div>


                <div>
                  <h1><i class="fa fa-cog"></i>Dealat Control panel</h1>
                  
                </div>
            </form>
          </section>
        </div>

      </div>
    </div>
  </body>
</html>