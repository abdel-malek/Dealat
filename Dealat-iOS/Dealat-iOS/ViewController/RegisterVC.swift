//
//  RegisterVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/14/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class RegisterVC: BaseVC {
    
    //    @IBOutlet weak var tfEmail : UITextField!
    //    @IBOutlet weak var tfPassword : UITextField!
    //    @IBOutlet weak var tfConfirmPassword : UITextField!
    
    
    @IBOutlet weak var img: UIImageView!
    @IBOutlet weak var tfName : UITextField!
    @IBOutlet weak var tfPhone: UITextField!
    
    /*@IBOutlet weak var tfLocation : UITextField!
    
    var locations = [Location]()
    var locationPicker : UIPickerView!
    var selectedLocation : Location!{
        didSet{
            if let loc = selectedLocation{
                self.tfLocation.text = loc.getLocName()
            }else{
                self.tfLocation.text = nil
            }
        }
    }*/

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        
        self.navigationController?.setNavigationBarHidden(true, animated: false)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        self.img.image = Provider.logoImage
        
        self.getData()
        
        
        Provider.setScreenName("RegisterActivity")
    }
    
    override func setupViews() {
        
        self.tfName.placeholder = self.tfName.placeholder!
        self.tfPhone.placeholder = self.tfPhone.placeholder!
//        self.tfLocation.placeholder = self.tfLocation.placeholder! + "*"
        
        self.tfName.placeHolderColor = Theme.Color.White
        self.tfPhone.placeHolderColor = Theme.Color.White
//        self.tfLocation.placeHolderColor = Theme.Color.White
//
//        setPickerViewOn(tfLocation)
    }
    
    /*func setPickerViewOn(_ textField : UITextField){
        locationPicker = UIPickerView(frame:CGRect(x: 0, y: 0, width: self.view.frame.size.width, height: 216))
        locationPicker.delegate = self
        locationPicker.dataSource = self
        
        let c = UIColor.init(red: 215/255, green: 217/255, blue: 223/255, alpha: 1)
        locationPicker.setValue(c, forKey: "backgroundColor")
        textField.inputView = locationPicker
    }
    
    override func getRefreshing() {
        Communication.shared.get_data_lists { (locations, _, _, _) in
            self.hideLoading()
            self.locations = locations
            self.locationPicker.reloadAllComponents()
        }
    }
    */
    
    @IBAction func registerAction(){
     
        let name = tfName.text!
        var phone = tfPhone.text!
        
        if name.isEmpty{
            self.showErrorMessage(text: "Please enter name")
        }else if phone.count != 10 {
            self.showErrorMessage(text: "Please enter valid phone number")
        }else{
            
            let alert = UIAlertController.init(title: phone, message: "numberConfirmation".localized, preferredStyle: .alert)
            
            alert.addAction(UIAlertAction.init(title: "OK".localized, style: .default, handler: { (ac) in
                
                phone.removeFirst()

                self.showLoading()
                Communication.shared.users_register(phone: Provider.getEnglishNumber(phone), name: name, callback: { (res) in
                    self.hideLoading()
                    
                    let vc = self.storyboard?.instantiateViewController(withIdentifier: "VerificationVC") as! VerificationVC
                    self.navigationController?.pushViewController(vc, animated: true)
                })
            }))
            
            alert.addAction(UIAlertAction.init(title: "Cancel".localized, style: .cancel, handler: nil))
            
            self.present(alert, animated: true, completion: nil)
        }
        
    }
    
    @IBAction func skipAction(){
        Provider.goToHome()
    }

    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        if textField == tfPhone{
            if (textField.text!.count < 3 && string == "") ||
                (textField.text!.count == 10  && string != ""){
                return false
            }
            else if !string.isEmpty{
                tfPhone.text = Provider.getEnglishNumber(textField.text! + string)
                return false
            }
        }
        
        
        
        return true
    }

    
}

/*extension RegisterVC : UIPickerViewDelegate, UIPickerViewDataSource{
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        
        return self.locations.count + 1
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        
        if row == 0{
            return "select one"
        }
        
        let loc = self.locations[row - 1]
        return loc.location_name
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        
        if row == 0{
            self.selectedLocation = nil
            return
        }
        self.selectedLocation = self.locations[row - 1]
    }
    
    func pickerView(_ pickerView: UIPickerView, attributedTitleForRow row: Int, forComponent component: Int) -> NSAttributedString? {
        if row == 0{
            return NSAttributedString.init(string: "select one", attributes: [NSAttributedStringKey.foregroundColor : UIColor.lightGray])
        }
        return nil
    }
    
}

*/
