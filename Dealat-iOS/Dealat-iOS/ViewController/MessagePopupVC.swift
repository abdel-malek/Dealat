//
//  MessagePopupVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 12/18/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class MessagePopupVC: BaseVC {
    
    @IBOutlet weak var titleLbl : UILabel!
    @IBOutlet weak var messageLbl : UITextView!
    @IBOutlet weak var viewBtn : UIButton!
    @IBOutlet weak var cancelkBtn : UIButton!
    @IBOutlet weak var vv : UIView!
    
    var tit : String!
    var message : String!
    var viewString : String!
    var cancelString : String!
    var publicNot : Bool! = false
    var data : [AnyHashable: Any]?
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.titleLbl.text = self.tit
        self.messageLbl.text = self.message
        
        self.viewBtn.setTitle(viewString, for: UIControl.State.normal)
        self.cancelkBtn.setTitle(cancelString, for: UIControl.State.normal)
        
        self.viewBtn.isHidden = publicNot
    }
    
    
    @IBAction func viewAction(){
        self.dismiss(animated: false, completion: nil)
        PushManager.handleNotificationTapping2(data: data)
    }
    
    @IBAction func cancelAction(){
        self.dismiss(animated: true, completion: nil)
    }
    
    
}
