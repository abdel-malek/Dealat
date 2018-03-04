//
//  VerificationVC.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 3/4/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class VerificationVC: BaseVC {

    @IBOutlet weak var tfCode: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.tfCode.placeHolderColor = Theme.Color.White

    }
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        
        self.navigationController?.setNavigationBarHidden(true, animated: false)
    }

    
    @IBAction func verificationAction(){
        
        let code = tfCode.text!
        
        if code.isEmpty{
            self.showErrorMessage(text: "Please enter code")
        }else{
            
            self.showLoading()
            Communication.shared.verify(Provider.getEnglishNumber(code), callback: { (res) in
                self.hideLoading()
                Provider.goToHome()
            })
        }
        
    }
    
    @IBAction func skipAction(){
        Provider.goToHome()
    }

}
