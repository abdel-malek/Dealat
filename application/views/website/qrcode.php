<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
		var base_url = "<?php echo base_url() . 'index.php'; ?>";
		var site_url = "<?php echo base_url() ; ?>";
		var lang = "<?php echo $this->session->userdata('language') ?>";
	</script>
	<title>Dealat</title>
	<!--  bootstrap  -->
	<link rel="stylesheet" href="<?php echo base_url('assets/css/bootstrap.min.css'); ?>" />
	<!--  main css style file  -->
	<link rel="stylesheet" href="<?php echo base_url('assets/css/style.css'); ?>" />
	<?php if($this->session->userdata('language') == "ar"){ ?>
	<link rel="stylesheet" href="<?php echo base_url('assets/css/style-ar.css'); ?>" />
	<?php }	?>
</head>
<body class="qr-page">
<div class="container">
	<span class="language-wrapper">
					<span class="language-switch">
						<?php if( $this->session->userdata("language")  == "en" ) $en_lng ="selected"; else $en_lng="";  ?>
						<?php if( $this->session->userdata("language")  == "ar" ) $ar_lng ="selected"; else $ar_lng="";  ?>
				<span class="english <?php echo $en_lng; ?>"  data-locale="en"><?php echo $this->lang->line('english'); ?></span>
					<span class="arabic <?php echo $ar_lng; ?>" data-locale="ar"><?php echo $this->lang->line('arabic'); ?></span>
					</span>
					</span>
<div align="center">
	<?php
	if($QR_path)
	{
	?>

		<div class="error-message d-none"></div>
		<br><div class="mb-2"><?php echo $this->lang->line('qr_page_note'); ?></div><br>
		<img class="qr-img" width="220px" src="<?php echo base_url($QR_path); ?>" alt="QRCode Image">
		
		<div class="note"><?php echo $this->lang->line('qr_note'); ?></div>
				<form id="qr-form">
				<div class="form-group">
				<input type='hidden'  class='qr_gen_code'  value="<?php echo $gen_code ?>"/>
					<input type="number" class="form-control" name="secret_code" placeholder="<?php echo $this->lang->line('enter_digits'); ?>">
					</div>
					<button type="submit" class="btn button2 submit" disabled><?php echo $this->lang->line('ok'); ?></button>
				</form>
	<?php
	}
	?>
</div>
</div>
<!--  jQuery library  -->
<script src="<?php echo base_url('assets/js/jquery-3.2.1.min.js'); ?>"></script>
<!--  bootstrap  -->
<script src="<?php echo base_url('assets/js/popper.min.js'); ?>"></script>
<script src="<?php echo base_url('assets/js/bootstrap.min.js'); ?>"></script>
<!-- main js file -->
<script src="<?php echo base_url('assets/js/qrcode.js'); ?>"></script>
</body>
</html>