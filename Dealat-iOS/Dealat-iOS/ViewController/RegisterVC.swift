//
//  RegisterVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/14/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class RegisterVC: BaseVC {

    @IBOutlet weak var tfEmail : UITextField!
    @IBOutlet weak var tfPassword : UITextField!
    @IBOutlet weak var tfConfirmPassword : UITextField!
    @IBOutlet weak var tfLocation : UITextField!

    @IBOutlet weak var tfLocation2 : UITextField!

    
    override func viewDidLoad() {
        super.viewDidLoad()

        
    }

    override func setupViews() {
        self.tfEmail.placeHolderColor = Theme.Color.White
        self.tfPassword.placeHolderColor = Theme.Color.White
        self.tfConfirmPassword.placeHolderColor = Theme.Color.White
        self.tfLocation.placeHolderColor = Theme.Color.White
    }
    
    

}
