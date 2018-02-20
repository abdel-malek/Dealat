<!DOCTYPE HTML>
<html lang="en">

<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" , shrink-to-fit=no />
	<script type="text/javascript" >
	var base_url = "<?php echo base_url() . 'index.php'; ?>";
    var site_url = "<?php echo base_url() ; ?>";
	var lang = "<?php echo $this->session->userdata('language') ?>";
</script>
	<title>
Dealat
	</title>
	<!--  bootstrap  -->
	<link rel="stylesheet" href="<?php echo base_url("assets/css/bootstrap.min.css"); ?>" />
	
	<!--  font-awesome  -->
	<link rel="stylesheet" href="<?php echo base_url("assets/css/font-awesome.css"); ?>" />

<!--  multi level dropdown  -->
	<link rel="stylesheet" href="<?php echo base_url("assets/css/bootstrap-4-navbar.css"); ?>" />
	
	<!--  sumo select  -->
	<link rel="stylesheet" href="<?php echo base_url("assets/css/sumoselect.min.css"); ?>" />

	<!--  file upload  -->
	<link rel="stylesheet" href="<?php echo base_url("assets/css/uploadfile.css"); ?>" />

	<!--  slick slider  -->
	<link rel="stylesheet" href="<?php echo base_url("assets/css/slick.css"); ?>" />
	<link rel="stylesheet" href="<?php echo base_url("assets/css/slick-theme.css"); ?>" />

	<!--  animate  -->
	<link rel="stylesheet" href="<?php echo base_url("assets/css/animate.css"); ?>" />

	<!--  main css style file  -->
	<link rel="stylesheet" href="<?php echo base_url("assets/css/style.css"); ?>" />
<?php if($this->session->userdata('language') == "ar"){ ?>
<link rel="stylesheet" href="<?php echo base_url("assets/css/style-ar.css"); ?>" />
<?php }	?>
	<!--[if lt IE 9]>
	<script src="js/html5shiv.min.js"></script>
    <![endif]-->

</head>
