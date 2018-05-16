<!DOCTYPE html>
<html>
<head>
	<title>Dealat</title>
</head>
<body>
<div align="center">
	<?php
	if($QR_path)
	{
	?>
		<br><br>Your QRcode Image here. Scan this to login<br>
		<img src="<?php echo base_url($QR_path); ?>" alt="QRCode Image">
		<input type='hidden'  id = 'qr_gen_code'  value=<?php echo $gen_code ?>/>
	<?php
	}
	?>
</div>
</body>
</html>