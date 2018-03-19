<?php 
class STATUS{
	
	const PENDING = 1;
	const ACCEPTED = 2;
	const EXPIRED = 3;
	const HIDDEN = 4;
	const REJECTED = 5;
	const DELETED = 6;
	
    public static function get_name($id)
	{
			switch($id) {
			case 1 :
				return 'pending';
				break;
			case 2 :
				return 'Accepted';
				break;
			case 3 :
				return 'Expired';
				break;
			case 4 :
				return 'Hidden';
				break;
			case 5 :
				return 'Rejected';
				break;
			case 6 :
				return 'Deleted';
				break;
			default :
				return 'no net';
			}
	}
	
	public static function get_list()
	{
		return array(
		   1 => 'pending',
		   2 => 'Accepted',
		   //3 => 'Expired',
		   4 => 'Hidden',
		   5 => 'Rejected',
		   6 => 'Deleted'
		);
	}
}
