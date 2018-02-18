//
//  CategoriesVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/11/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class CategoriesVC: BaseVC {
    
    
    @IBOutlet weak var tableView : UITableView2!
    
    var cat : Cat = Cat()
    var chooseCatVC : ChooseCatVC!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = cat.category_name
        
        tableView.delegate = self
        tableView.dataSource = self
        
    }
    
}

extension CategoriesVC : UITableViewDelegate, UITableViewDataSource{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return cat.children.count + 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! CategoryCell
        
        if indexPath.row == 0{
            if let c = Cat(JSON: self.cat.toJSON()){
                c.category_name = "All"
                cell.tag = -1
                cell.cat = c
            }
        }else{
            cell.cat = cat.children[indexPath.row - 1]
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if indexPath.row == 0{
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "AdsListVC") as! AdsListVC
            vc.cat = self.cat
            self.chooseCatVC.dismiss(animated: false, completion: {
                self.chooseCatVC.homeVC.navigationController?.pushViewController(vc, animated: true)
            })
            
        }else{
            let c = cat.children[indexPath.row - 1]
            if c.children.count > 1 {
                let vc = self.storyboard?.instantiateViewController(withIdentifier: "CategoriesVC") as! CategoriesVC
                vc.chooseCatVC = self.chooseCatVC
                vc.cat = c
                self.navigationController?.pushViewController(vc, animated: true)
                
            }else{
                let vc = self.storyboard?.instantiateViewController(withIdentifier: "AdsListVC") as! AdsListVC
                vc.cat = c
                self.chooseCatVC.dismiss(animated: false, completion: {
                    self.chooseCatVC.homeVC.navigationController?.pushViewController(vc, animated: true)
                })
            }
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 50
    }
    
    
    
}
